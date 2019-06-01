import Config from "./config";
import Cookie from './cookie';

import ReactDOM from "react-dom";
import Alert from "./Public/Alert";
import React from "react";

let utils = {};

utils.url = function(url){
    url = "/"+url.replace(/-/g, "/");
    url += ".do";
    return url;
}

utils.requestUrl = function(url){
    url = url.replace("-", "/");
    return url;
}

utils.jsonToForm = function(json){
    let form = "";
    for(let j in json){
        form += j+"="+json[j]+"&"
    }
    if(form.length>0) form = form.substring(0, form.length-1);
    return form;
}

utils.request = function(url, body, success, fail){
    let $this = this;
    $this.showLoading();
    fetch(url, {
        method: "POST",
        mode: "cors",
        body: body,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
    }).then(res=>res.text()).then(data=>{
        $this.hideLoading();
        data = data ? JSON.parse(data) : null;
        if(data) {
            if(data.code===-1){
                $this.alert(data.message, function(){
                    window.location.href = $this.url('member-login');
                })
                return false;
            }
            if(data.code===1 && success) success(data.message);
            if(data.code===0 && fail) fail(data.message);
        }
    });
}

utils.upload = function(url, body, success, fail){
    let $this = this;
    $this.showLoading();
    fetch(url, {
        method: "POST",
        mode: "cors",
        body: body,
        headers: {

        },
    }).then(res=>res.text()).then(data=>{
        $this.hideLoading();
        data = data ? JSON.parse(data) : null;
        if(data) {
            if(data.code===1 && success) success(data.message);
            if(data.code===0 && fail) fail(data.message);
        }
    });
}

utils.checkMember = function(callback){
    let c = this.getLoginCookie();
    this.request(Config.requestUrl+this.requestUrl('member-read'), "cookie=" + encodeURIComponent(c), function(data){
        if(callback) callback(data);
    });
}

utils.getLoginCookie = function(){
    return Cookie.load("loginCookie");
}

utils.alert = function(message, callback, time){
    let div = document.createElement('div');
    if(typeof callback==='function'){
        if(time>0) {
            ReactDOM.render(<Alert message={message} />, div);
            setTimeout(callback, time);
        }
        if(!time) {
            ReactDOM.render(<Alert message={message} callback={callback} />, div)
        }
    }else{
        ReactDOM.render(<Alert message={message} />, div);
    }
    document.body.appendChild(div);
}

utils.timeToDate = function(time){
    if(isNaN(time-1)) return null;
    let date = new Date(time*1000);
    return date.getFullYear() + '-' + this.addZero(date.getMonth()+1,2) + '-' + this.addZero(date.getDay(),2) + ' ' + this.addZero(date.getHours(),2) + ':' + this.addZero(date.getMinutes(),2) + ':' + this.addZero(date.getSeconds(),2);
}
utils.addZero = function(number, count){
    while((number+'').length<count){
        number = '0' + number;
    }
    return number;
}

// Loading animation
utils.showLoading = function(){
    window.loading = true;
}
utils.hideLoading = function(){
    window.loading = false;
}

export default utils;
