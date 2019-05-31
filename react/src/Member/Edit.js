import React from 'react';

import Config from '../config';
import Utils from '../utils';
import './Edit.css';
import Sider from "../Public/Sider";
import Header from "../Public/Header";

class Edit extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Member Edit';
        let $this = this;
        $this.state = {
            memberCopy: {},
            member: {username: '', email: '', group:'', groupObj: {}},
            id: props.match.params.id
        };
        Utils.checkMember(function(res){
            if(res.group!==1 && res.id!==parseInt($this.state.id)){
                Utils.alert('Access denied', function(){
                    window.location.href = Utils.url('index-show');
                });
                return false;
            }else if(res.group===1){
                Utils.request(Config.requestUrl+Utils.requestUrl('member-readById'), 'id='+$this.state.id, function(member){
                    $this.setState({
                        member: member,
                        memberCopy: res
                    })
                })
            }else{
                $this.setState({
                    member: res,
                    memberCopy: res
                })
            }
        })
    }

    input(e){
        e.preventDefault();
        let member = this.state.member;
        e = e.target;
        member[e.name] = e.value;
        this.setState({
            member: member
        });
    }

    submit(e){
        e.preventDefault();
        let postdata = this.state.member;
        postdata.cookie = Utils.getLoginCookie();
        Utils.request(Config.requestUrl+Utils.requestUrl('member-edit'), Utils.jsonToForm(postdata), function(res){
            Utils.alert(res);
        }, function(msg){
            Utils.alert(msg);
        });
    }

    render(){
        let member = this.state.member;
        return (
            <div>
                <Sider member={this.state.memberCopy} active1="Manage Member"></Sider>
                <Header title1={null} title2="Manage Member"></Header>

                <div className="wrap member-edit">
                    <div className="item">
                        <div className="title">MemberName:</div>
                        <div className="value">{member.membername}</div>
                    </div>
                    <div className="item">
                        <div className="title">Group:</div>
                        <div className="value">{member.groupObj.name}</div>
                    </div>
                    <div className="item">
                        <div className="title">RealName:</div>
                        <div className="value">{member.realName}</div>
                    </div>
                    <div className="item">
                        <div className="title">Email:</div>
                        <div className="value">
                            <input className="form-control" value={member.email} name="email" onChange={this.input.bind(this)} />
                        </div>
                    </div>
                    <div className="item">
                        <div className="title">Password:</div>
                        <div className="value">
                            <input className="form-control" value={member.password || ''} name="password" type="password" onChange={this.input.bind(this)} />
                        </div>
                    </div>
                    <button className="btn btn-success" onClick={this.submit.bind(this)}>Submit</button>
                </div>
            </div>
        )
    }
}

export default Edit;
