import React from 'react';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';

import Utils from '../utils';
import Config from '../config';
import './View.css';

import Header from '../Public/Header'
import Sider from '../Public/Sider'

class View extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Submission View';
        let $this = this;
        let type = props.match.params.type;
        $this.state = {
            id: this.props.location.search.replace('?id=', ''),
            type: type,
            typeUpper: type.substring(0,1).toUpperCase() + type.substring(1),
            member: {groupObj: {}},
            submission: {},
            list: [],
            canSubmit: true
        };
        Utils.checkMember(function(res){
            $this.setState({
                member: res
            });
            let postdata = {};
            postdata.cookie = Utils.getLoginCookie();
            postdata.id = $this.state.id;
            Utils.request(Config.requestUrl+Utils.requestUrl('submission-read'), Utils.jsonToForm(postdata), function(res){
                $this.setState({
                    submission: res
                });
                postdata = {};
                postdata.id = $this.state.id;
                postdata.cookie = Utils.getLoginCookie();
                Utils.request(Config.requestUrl+Utils.requestUrl('feedback-list'), Utils.jsonToForm(postdata), function(res){
                    $this.setState({
                        list: res
                    });
                    for(let i=0; i<res.length; i++){
                        if(res[i].mid===$this.state.member.id){
                            $this.setState({
                                canSubmit: false
                            });
                            return false;
                        }
                    }
                });
            });

        });
    }

    submit() {
        let $this = this;
        confirmAlert({
            title: 'Confirm',
            message: 'Confirm to submit your feedback?',
            buttons: [
                {label: 'Cancel', onClick: () => {}},
                {label: 'Confirm',
                    onClick: () => {
                        let postdata = {};
                        postdata.cookie = Utils.getLoginCookie();
                        postdata.id = $this.state.id;
                        postdata.score = document.getElementById('submitScore').value;
                        postdata.content = document.getElementById('submitContent').value;
                        if(postdata.score==='0'){
                            Utils.alert('Please grade the submission');
                            return false;
                        }
                        if(postdata.content===''){
                            Utils.alert('Please enter you review');
                            return false;
                        }
                        Utils.request(Config.requestUrl+Utils.requestUrl('feedback-submit'), Utils.jsonToForm(postdata), function(res){
                            Utils.alert(res, function(){
                                window.location.reload();
                            });
                        }, function(err){
                            Utils.alert(err);
                        });
                    }}
            ]
        });
    }

    render(){
        let member = this.state.member;
        let list = this.state.list;
        let submission = this.state.submission;
        return (
            <div>
                <Sider member={member} active1={'Project '+this.state.typeUpper} active2={'Manage Project '+this.state.typeUpper}></Sider>
                <Header title1={'Project '+this.state.typeUpper} title2={'View Project '+this.state.typeUpper}></Header>

                <div className="wrap">

                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <th>Student ID</th>
                                <th>Document</th>
                                <th>Submit Time</th>
                                <th>Grade Time</th>
                                <th>Grade</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>{submission.mid}</td>
                                <td><a href={Config.requestUrl+Utils.requestUrl('submission-download')+'?cookie='+Utils.getLoginCookie()+'&id='+submission.id}>Download</a></td>
                                <td>{Utils.timeToDate(submission.submitTime)}</td>
                                <td>{Utils.timeToDate(submission.gradeTime)}</td>
                                <td>{Config.scores[submission.score]}</td>
                            </tr>
                        </tbody>
                    </table>

                    {list.length>0?
                    <div className="feedback-list">
                        <div className="title">Feedback List</div>
                        {list.map((l, k)=>{
                            return <div key={k} className="item">
                                <div className="info">
                                    <span className="fa fa-user"></span>
                                    <span>Member ID: {l.mid}</span>
                                    <span className="score">Grade: {Config.scores[l.score]}</span>
                                </div>
                                <div className="content">{l.content}</div>
                            </div>
                        })}
                    </div>
                    :null}

                    {this.state.canSubmit && submission.mid!==member.id ?
                    <div className="feedback-box">
                        <div className="title">Your Feedback:</div>
                        <select className="form-control" id="submitScore">
                            {Config.scores.map((s, k)=>{
                                return <option key={k} value={k}>{s}</option>
                            })}
                        </select>
                        <textarea className="form-control" placeholder="Please enter your review of thie submission" id="submitContent"></textarea>
                        <button className="btn btn-success" onClick={this.submit.bind(this)}>Submit Feedback</button>
                    </div>:null}
                </div>
            </div>
        )
    }
}

export default View;
