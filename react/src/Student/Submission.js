import React from 'react';
import Utils from '../utils';
import Config from '../config';
import './List.css';

import Header from '../Public/Header'
import Sider from '../Public/Sider'

class Submission extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Student Submission List';
        let $this = this;
        $this.state = {
            member: {groupObj: {}},
            list: [],
            types: ['', 'description', 'plan', 'report']
        };
        Utils.checkMember(function(res){
            $this.setState({
                member: res
            });
            let postdata = {};
            postdata.cookie = Utils.getLoginCookie();
            postdata.id = props.match.params.id;
            Utils.request(Config.requestUrl+Utils.requestUrl('submission-listByStudent'), Utils.jsonToForm(postdata), function(res){
                $this.setState({
                    list: res
                });
            });
        });
    }

    render(){
        let member = this.state.member;
        return (
            <div>
                <Sider member={member} active1="Manage Member" active2="Student List"></Sider>
                <Header title1="Manage Member" title2="Student List"></Header>

                <div className="wrap">

                    <table className="table table-striped">
                        <thead>
                        <tr>
                            <th>Student ID</th>
                            <th>Type</th>
                            <th>Document</th>
                            <th>Submit Time</th>
                            <th>Grade Time</th>
                            <th>Grade</th>
                            <th>Operation</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.list.map((l, k)=>{
                            return <tr key={k}>
                                <td>{l.mid}</td>
                                <td>Project {this.state.types[l.type]}</td>
                                <td><a href={Config.requestUrl+Utils.requestUrl('submission-download')+'?cookie='+Utils.getLoginCookie()+'&id='+l.id}>Download</a></td>
                                <td>{Utils.timeToDate(l.submitTime)}</td>
                                <td>{Utils.timeToDate(l.gradeTime)}</td>
                                <td>{Config.scores[l.score]}</td>
                                <td><a href={Utils.url('submission-view-'+this.state.types[l.type])+'?id='+l.id}>View</a></td>
                            </tr>
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default Submission;
