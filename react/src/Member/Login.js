import React from 'react';

import Config from '../config';
import Utils from '../utils';
import Cookie from '../cookie';
import './Login.css';

class Login extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Member Login';
        this.state = {
            formData: {name:'', password: ''},
            disabled: false,
            submitText: 'Login'
        };
        this.input = this.input.bind(this);
        this.login = this.login.bind(this);
    }

    // 输入
    input(e){
        e.preventDefault();
        let formData = this.state.formData;
        e = e.target;
        formData[e.name] = e.value;
        this.setState({
            formData: formData
        });
    }

    login(e){
        e.preventDefault();
        let $this = this;
        let postdata = this.state.formData;
        $this.setState({ disabled: true, submitText: 'Submitting' });
        postdata = Utils.jsonToForm(postdata);
        Utils.request(Config.requestUrl+Utils.requestUrl('member-login'), postdata, function(data){
            Cookie.save("loginCookie", data);
            Utils.alert("Login successfully",function(){
                window.location.href = Utils.url("index-show");
            }, 1500);
        }, function(msg){
            $this.setState({ disabled: false, submitText: 'Login' });
            Utils.alert(msg);
        });
    }

    render(){
        let state = this.state;
        return (
            <div className="main">
                <img src={require('../Asset/Img/logo.png')} alt="" className="logo" />
                <div className="box">
                    <div className="row">
                        <div className="col-lg-4">Member Name</div>
                        <input type="text" className="form-control col-lg-8" value={state.formData.name} onChange={this.input} name="name" placeholder="Member Name" />
                    </div>
                    <div className="row mt-4">
                        <div className="col-lg-4">Password</div>
                        <input type="password" className="form-control col-lg-8" value={state.formData.password} onChange={this.input} name="password" placeholder="Password" />
                    </div>
                    <button className="btn btn-block btn-warning mt-5" disabled={state.disabled} onClick={this.login}>{state.submitText}</button>
                </div>
            </div>
        )
    }
}

export default Login;
