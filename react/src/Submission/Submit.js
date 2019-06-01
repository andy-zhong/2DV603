import React from 'react';
import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';

import Utils from '../utils';
import Config from '../config';
import './Submit.css';

import Header from '../Public/Header'
import Sider from '../Public/Sider'

class Submit extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Submission Submit';
        let $this = this;
        let type = props.match.params.type;
        $this.state = {
            type: type,
            typeUpper: type.substring(0,1).toUpperCase() + type.substring(1),
            supervisorId: this.props.location.search.replace('?id=', ''),
            file: null,
            member: {groupObj: {}},
            list: [],
            schedule: {},
            agreement: false
        };
        if(type==='plan' && props.location.search===''){
            Utils.alert('Please select a supervisor', function(){
                window.location.href = Utils.url('supervisor-list');
            });
            return false;
        }
        Utils.checkMember(function(res){
            $this.setState({
                member: res
            });
            let postdata = {};
            postdata.cookie = Utils.getLoginCookie();
            postdata.type = $this.state.type;
            Utils.request(Config.requestUrl+Utils.requestUrl('submission-listOfStudent'), Utils.jsonToForm(postdata), function(res){
                $this.setState({
                    list: res
                });
            });
            Utils.request(Config.requestUrl+Utils.requestUrl('schedule-read'), Utils.jsonToForm({type: 'Submit'+$this.state.type}), function(res){
                if(res===null){
                    Utils.alert('No schedule', function(){
                        window.location.href = Utils.url('index-show');
                    });
                    return false;
                }
                $this.setState({
                    schedule: res
                });
            })
        });
        this.fileSelect = this.fileSelect.bind(this);
        this.submit = this.submit.bind(this);
    }

    agreementChange(){
        let agree = !this.state.agreement;
        this.setState({
            agreement: agree
        })
    }

    submit() {
        if(!this.state.agreement){
            Utils.alert('Please select the agreement first')
            return false;
        }
        if(!this.state.file){
            Utils.alert('Please the file you want to submit')
            return false;
        }
        confirmAlert({
            title: 'Confirm',
            message: 'Confirm to submit the file?',
            buttons: [
                {label: 'Cancel', onClick: () => {}},
                {label: 'Confirm',
                    onClick: () => {
                        const formData = new FormData();
                        formData.append('file', this.state.file);
                        formData.append('type', this.state.type);
                        formData.append('cookie', Utils.getLoginCookie());
                        formData.append('supervisorId', this.state.supervisorId);
                        Utils.upload(Config.requestUrl+Utils.requestUrl('submission-submit'), formData, function(res){
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

    fileSelect(e) {
        let file = e.target.files[0];
        this.setState({
            file: file
        });
    }

    render(){
        let member = this.state.member;
        let list = this.state.list;
        let schedule = this.state.schedule;
        return (
            <div>
                <Sider member={member} active1={'Project '+this.state.typeUpper} active2={'Submit Project '+this.state.typeUpper}></Sider>
                <Header title1={'Project '+this.state.typeUpper} title2={'Submit Project '+this.state.typeUpper}></Header>

                <div className="wrap">

                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <th>Submission</th>
                                <th>{schedule.title}</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Submission status</td>
                                <td>{
                                        list.length>0 &&
                                        list[0].submitTime>=schedule.startTime &&
                                        list[0].submitTime<=schedule.endTime ?
                                            (list[0].score>0?'Graded':'Submitted for grading') :
                                            'Not submit'
                                }</td>
                            </tr>
                            <tr>
                                <td>Deadline</td>
                                <td>{Utils.timeToDate(schedule.endTime)}</td>
                            </tr>
                            <tr>
                                <td>Last modified</td>
                                <td>{
                                    list.length>0 &&
                                    list[0].submitTime>=schedule.startTime &&
                                    list[0].submitTime<=schedule.endTime ?
                                        Utils.timeToDate(list[0].submitTime) :
                                        'Not submit'
                                }</td>
                            </tr>
                            <tr>
                                <td>Grading status</td>
                                <td>{
                                    list.length>0 &&
                                    list[0].submitTime>=schedule.startTime &&
                                    list[0].submitTime<=schedule.endTime ?
                                        Config.scores[list[0].score] :
                                        'Not Graded'
                                }</td>
                            </tr>
                        </tbody>
                    </table>

                    {schedule.endTime<(new Date().getTime()/1000) || schedule.startTime>(new Date().getTime()/1000) ||
                    (list.length>0 && list[0].submitTime>=schedule.startTime && list[0].submitTime<=schedule.endTime) ? null :
                        <div>
                            <div className="agreement">
                                <input type="checkbox" checked={this.state.agreement} onChange={this.agreementChange.bind(this)} /> I declare that:
                                <p>This assignment is entirely my own work, except where I have included fully-documented references to the work of others</p>
                            </div>
                            <input type="file" className="form-control" placeholder="Choose your file" name="file" onChange={this.fileSelect} />
                            <button className="btn btn-primary" onClick={this.submit}>Submit</button>
                        </div>
                    }
                </div>
            </div>
        )
    }
}

export default Submit;
