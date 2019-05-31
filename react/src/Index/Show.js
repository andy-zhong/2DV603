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

                <div className="wrap index-show">
                    <h4>Hi, {member.realName}</h4>
                    <div className="item">
                        <div className="left">Your MemberName:</div>
                        <div className="right">{member.membername}</div>
                    </div>
                    <div className="item">
                        <div className="left">Your Group:</div>
                        <div className="right">{member.groupObj.name}</div>
                    </div>
                    <div className="item">
                        <div className="left">Your RealName:</div>
                        <div className="right">{member.realName}</div>
                    </div>
                    <div className="item">
                        <div className="left">Your Email:</div>
                        <div className="right">{member.email===''?'None':member.email}</div>
                    </div>
                    <a href={Utils.url('member-edit-'+member.id)} className="btn btn-success">Edit your information</a>
                </div>
            </div>
        )
    }
}

export default Show;
