import React from 'react';
import Utils from '../utils';
import Config from '../config';
import './List.css';

import Header from '../Public/Header'
import Sider from '../Public/Sider'

class List extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Student List';
        let $this = this;
        $this.state = {
            member: {groupObj: {}},
            list: []
        };
        Utils.checkMember(function(res){
            $this.setState({
                member: res
            });
            let postdata = {};
            postdata.cookie = Utils.getLoginCookie();
            postdata.group = 'student';
            Utils.request(Config.requestUrl+Utils.requestUrl('member-list'), Utils.jsonToForm(postdata), function(res){
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

                    <table className="table table-hover">
                        <thead className="thead-dark">
                            <tr>
                                <th>Member name</th>
                                <th>Real name</th>
                                <th>Email address</th>
                                <th>Operation</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.list.map((l, k)=>{
                                return <tr key={k}>
                                    <td>{l.membername}</td>
                                    <td>{l.realName}</td>
                                    <td>{l.email}</td>
                                    <td>
                                        <a href={Utils.url('student-submission-'+l.id)}>Submissions</a>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        {member.group===1?
                                            <a href={Utils.url('member-edit-'+l.id)}>Edit</a>
                                            :null}

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
