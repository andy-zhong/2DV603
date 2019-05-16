import React from "react";

import Utils from "../utils";

import './Sider.css';

class Sider extends React.Component {

    constructor(props){
        super(props);
        const groups = ['student', 'supervisor', 'coordinator', 'reader'];
        this.state = {
            menu: [
                {
                    name: "Home",
                    url: Utils.url('index-show'),
                    icon: 'fa-home',
                    group: groups,
                    subShow: false,
                    sub: []
                },
                {
                    name: "Schedule",
                    url: Utils.url('schedule-show'),
                    icon: 'fa-calendar',
                    group: ['coordinator','student','supervisor','opponent','reader'],
                    subShow: false,
                    sub: []
                },
                {
                    name: "Manage Member",
                    url: 'javascript:;',
                    icon: 'fa-user',
                    group: groups,
                    subShow: false,
                    sub: [
                        {
                            name: "Student List",
                            url: Utils.url('student-list'),
                            group: ['coordinator', 'supervisor']
                        },
                        {
                            name: "Supervisor List",
                            url: Utils.url('supervisor-list'),
                            group: ['coordinator', 'supervisor','student']
                        },
                        {
                            name: "Opponent List",
                            url: Utils.url('oppoent-list'),
                            group: ['coordinator', 'student','opponent']
                        },
                        {
                            name: "Reader List",
                            url: Utils.url('reader-list'),
                            group: ['coordinator', 'reader']
                        }
                    ]
                },
                {
                    name: "Project Description",
                    url: 'javascript:;',
                    icon: 'fa-audio-description',
                    group: ['student', 'coordinator'],
                    subShow: false,
                    sub: [
                        {
                            name: "Submit Description",
                            url: Utils.url('description-submit'),
                            group: ['student', 'supervisor','coordinator']
                        },
                        {
                            name: "Manage Description",
                            url: Utils.url('description-manage'),
                            group: ['student', 'supervisor','coordinator']
                        }
                    ]
                },
                {
                    name: "Project Plan",
                    url: 'javascript:;',
                    icon: 'fa-file',
                    group: ['student', 'supervisor', 'coordinator'],
                    subShow: false,
                    sub: [
                        {
                            name: "Submit Project Plan",
                            url: Utils.url('projectplan-submit'),
                            group: ['student', 'supervisor', 'coordinator']
                        },
                        {
                            name: "Manage Project Plan",
                            url: Utils.url('projectplan-manage'),
                            group: ['student', 'supervisor', 'coordinator']
                        }
                    ]
                },
                {
                    name: "Project Report",
                    url: 'javascript:;',
                    icon: 'fa-file',
                    group: ['student', 'supervisor', 'coordinator'],
                    subShow: false,
                    sub: [
                        {
                            name: "Submit Project Report",
                            url: Utils.url('projectplan-submit'),
                            group: ['student', 'supervisor', 'coordinator']
                        },
                        {
                            name: "Manage Project Report",
                            url: Utils.url('projectplan-manage'),
                            group: ['student', 'supervisor', 'coordinator']
                        }
                    ]
                },
                {
                    name: "Logout",
                    url: Utils.url('member-logout'),
                    icon: 'fa-power-off',
                    group: groups,
                    subShow: false,
                    sub: []
                }
            ]
        };
    }

    getMenu(){
        let member = this.props.member;
        let active1 = this.props.active1;
        let active2 = this.props.active2;
        let menu = this.state.menu;
        let group = member.groupObj ? member.groupObj : {};
        let element = [];
        let element2 = [];
        for(let i=0; i<menu.length; i++){
            if(menu[i].group.indexOf(group.type)>-1){
                element2 = [];
                for(let k=0; k<menu[i].sub.length; k++){
                    if(menu[i].sub[k].group.indexOf(group.type)>-1){
                        element2.push(
                            <a className={'subItem '+(active2==menu[i].sub[k].name?'active':'')} href={menu[i].sub[k].url} key={k}>
                                <div className="first hide"></div>
                                <div className="text">{menu[i].sub[k].name}</div>
                                <div className="fa fa-angle-down hide"></div>
                            </a>
                        );
                    }
                }
                element.push(
                    <div className="item" key={i}>
                        <div className={'content '+(active1==menu[i].name?'active':'')} onClick={menu[i].sub.length>0?this.showSubMenu.bind(this, i):this.jump.bind(this, menu[i].url)}>
                            <div className={'fa '+(menu[i].icon)+' first'}></div>
                            <div className="text">{menu[i].name}</div>
                            <div className={'fa '+(menu[i].subShow?'fa-angle-up':'fa-angle-down')+' '+(menu[i].sub.length>0?'':'hide')}></div>
                        </div>
                        {menu[i].sub.length>0?<div className={'subMenu '+(menu[i].subShow?'show':'')}>{element2}</div>:null}
                    </div>
                );
            }
        }
        return element;
    }

    showSubMenu(id, e){
        e.preventDefault();
        this.state.menu[id].subShow = !this.state.menu[id].subShow;
        this.setState({
            menu: this.state.menu
        });
    }

    jump(url, e){
        e.preventDefault();
        window.location.href = url;
    }

    render(){
        return (
            <div className="sider">
                <div className="top">
                    <img src={require('../Asset/Img/logo.png')} className="logo" />
                    <span className="fa fa-bars"></span>
                </div>
                <div className="menu">
                    {this.getMenu()}
                </div>
            </div>
        )
    }

}

export default Sider;
