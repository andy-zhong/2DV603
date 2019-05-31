import React from 'react';
import Utils from '../utils';
import './List.css';

import Header from '../Public/Header'
import Sider from '../Public/Sider'
import Config from "../config";

class List extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Supervisor List';
        let $this = this;
        $this.state = {
            member: {groupObj: {}},
            list: [],
            student: []
        };
        Utils.checkMember(function(res){
            $this.setState({
                member: res
            });
            let postdata = {};
            postdata.cookie = Utils.getLoginCookie();
            postdata.group = 'supervisor';
            Utils.request(Config.requestUrl+Utils.requestUrl('member-list'), Utils.jsonToForm(postdata), function(res){
                $this.setState({
                    list: res
                });
                let student = $this.state.student;
                let requestCount = res.length;
                for(let i=0; i<res.length; i++){
                    Utils.request(Config.requestUrl+Utils.requestUrl('member-studentListOfSupervisor'), 'id='+res[i].id, function(m){
                        student[i] = m;
                        requestCount--;
                    })
                }
                let interval = setInterval(function(){
                    if(requestCount<=0){
                        $this.setState({
                            student: student
                        })
                        clearInterval(interval);
                    }
                }, 100);
            });
        });
    }

    render(){
        let member = this.state.member;
        return (
            <div>
                <Sider member={member} active1="Manage Member" active2="Supervisor List"></Sider>
                <Header title1="Manage Member" title2="Supervisor List"></Header>

                <div className="wrap">

                    <table className="table table-hover">
                        <thead>
                            <tr>
                                <th>Supervisor name</th>
                                <th>Email address</th>
                                <th>Supervising Student</th>
                                <th>Operation</th>
                            </tr>
                        </thead>
                        <tbody>
                        {this.state.list.map((l, k)=>{
                            return <tr key={k}>
                                <td>{l.membername}</td>
                                <td>{l.email}</td>
                                <td>
                                    {this.state.student[k] && this.state.student[k].length>0?this.state.student[k].map((s, k2)=>{
                                        return <span key={k2} className="apply-item">{s.membername}</span>
                                    }):'None'}
                                </td>
                                <td>
                                    {member.group === 1 ?
                                        <a href={Utils.url('member-edit-'+l.id)}>Edit</a>:
                                        (member.group === 3 ? <a href={Utils.url('submission-submit-plan') + '?id=' + l.id}>Apply</a>:'None')
                                    }
                                </td>
                            </tr>
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default List;
