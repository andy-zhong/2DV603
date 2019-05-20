import React from 'react';
import Utils from '../utils';
import Config from '../config';
import './List.css';

import Header from '../Public/Header'
import Sider from '../Public/Sider'

class List extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Supervisor List';
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
                <Sider member={member} active1="Manage Member" active2="Supervisor List"></Sider>
                <Header title1="Manage Member" title2="Supervisor List"></Header>

                <div className="wrap">

                    <table className="table table-hover">
                        <thead>
                        <tr>
                            <th>Supervisor name</th>
                            <th>Email address</th>
                            <th>Supervising Student</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Meow Meow</td>
                            <td>Meow@lnu.se</td>
                            <td>Meow, Nameow</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>2</td>
                            <td>2</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>3</td>
                            <td>3</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default List;