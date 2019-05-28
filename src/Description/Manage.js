import React from 'react';

import Utils from '../utils';
import './Manage.css';

import Header from '../Public/Header'
import Sider from '../Public/Sider'

class Manage extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Manage Description';
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
                <Sider member={member} active1="Project Description" active2="Manage Description"></Sider>
                <Header title1="Project Description" title2="Manage Description"></Header>

                <div className="wrap">
                    <h4>Hi, {member.realName} ({member.groupObj.name})</h4>
                </div>
            </div>
        )
    }
}

export default Manage;