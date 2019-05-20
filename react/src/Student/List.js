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
            member: {groupObj: {}}
        };
        Utils.checkMember(function(res){
            $this.setState({
                member: res
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
                        <thead>
                        <tr>
                            <th>Student name</th>
                            <th>Email address</th>
                            <th>Submission</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Meow Meow</td>
                            <td>aa111zz@student.lnu.se</td>
                            <td>abc</td>
                        </tr>
                        <tr>
                            <td>abc</td>
                            <td>abc</td>
                            <td>abc</td>
                        </tr>
                        <tr>
                            <td>abc</td>
                            <td>abc</td>
                            <td>abc</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default List;