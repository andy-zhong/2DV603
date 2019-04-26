import React from 'react';

import Utils from '../utils';
import './Show.css';

import Header from '../Public/Header'
import Sider from '../Public/Sider'

class Show extends React.Component {

    constructor(props){
        super(props);
        document.title = 'home';
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
                <Sider member={member} active1="Home"></Sider>
                <Header title1={null} title2="Home"></Header>

                <div className="wrap">
                    <h4>Hi, {member.realName} ({member.groupObj.name})</h4>
                </div>
            </div>
        )
    }
}

export default Show;
