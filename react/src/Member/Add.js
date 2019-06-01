import React from 'react';

import Config from '../config';
import Utils from '../utils';
import './Edit.css';
import Sider from "../Public/Sider";
import Header from "../Public/Header";

class Add extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Member Add';
        let $this = this;
        $this.state = {
            member: {},
            data: {membername: '', password: '', email: '', group: 1, realName: ''},
            group: []
        };
        Utils.checkMember(function(res){
            if(res.group!==1){
                Utils.alert('Access denied', function(){
                    window.location.href = Utils.url('index-show');
                });
                return false;
            }
            $this.setState({
                member: res
            });
            Utils.request(Config.requestUrl+Utils.requestUrl('member-group'), '', function(res){
                $this.setState({
                    group: res
                })
            })
        })
    }

    input(e){
        e.preventDefault();
        let data = this.state.data;
        e = e.target;
        data[e.name] = e.value;
        this.setState({
            data: data
        });
    }

    submit(e){
        e.preventDefault();
        let postdata = this.state.data;
        postdata.cookie = Utils.getLoginCookie();
        Utils.request(Config.requestUrl+Utils.requestUrl('member-add'), Utils.jsonToForm(postdata), function(res){
            Utils.alert(res, function(){
                window.location.reload();
            });
        }, function(msg){
            Utils.alert(msg);
        });
    }

    render(){
        let data = this.state.data;
        return (
            <div>
                <Sider member={this.state.member} active1="Manage Member" active2="Add Member"></Sider>
                <Header title1={null} title2="Add Member"></Header>

                <div className="wrap member-edit">
                    <div className="item">
                        <div className="title">MemberName:</div>
                        <div className="value">
                            <input className="form-control" value={data.membername||''} name="membername" onChange={this.input.bind(this)} />
                        </div>
                    </div>
                    <div className="item">
                        <div className="title">Password:</div>
                        <div className="value">
                            <input className="form-control" type="password" value={data.password||''} name="password" onChange={this.input.bind(this)} />
                        </div>
                    </div>
                    <div className="item">
                        <div className="title">Group:</div>
                        <div className="value">
                            <select className="form-control" value={data.group} name="group" onChange={this.input.bind(this)}>
                                {this.state.group.map((g, k)=>{
                                    return <option value={g.id} key={k}>{g.name}</option>
                                })}
                            </select>
                        </div>
                    </div>
                    <div className="item">
                        <div className="title">RealName:</div>
                        <div className="value">
                            <input className="form-control" value={data.realName||''} name="realName" onChange={this.input.bind(this)} />
                        </div>
                    </div>
                    <div className="item">
                        <div className="title">Email:</div>
                        <div className="value">
                            <input className="form-control" value={data.email||''} name="email" onChange={this.input.bind(this)} />
                        </div>
                    </div>
                    <button className="btn btn-success" onClick={this.submit.bind(this)}>Submit</button>
                </div>
            </div>
        )
    }
}

export default Add;
