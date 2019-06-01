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
                    subShow: this.props.active1==='Home',
                    sub: []
                },
                {
                    name: "Schedule",
                    url: Utils.url('schedule-show'),
                    icon: 'fa-calendar',
                    group: ['coordinator','student','supervisor','opponent','reader'],
                    subShow: this.props.active1==='Schedule',
                    sub: []
                },
                {
                    name: "Manage Member",
                    url: '#',
                    icon: 'fa-user',
                    group: ['coordinator', 'supervisor', 'student'],
                    subShow: this.props.active1==='Manage Member',
                    sub: [
                        {
                            name: "Add Member",
                            url: Utils.url('member-add'),
                            group: ['coordinator']
                        },
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
                            name: "Reader List",
                            url: Utils.url('reader-list'),
                            group: ['coordinator']
                        }
                    ]
                },
                {
                    name: "Project Description",
                    url: '#',
                    icon: 'fa-audio-description',
                    group: ['student', 'coordinator'],
                    subShow: this.props.active1==='Project Description',
                    sub: [
                        {
                            name: "Submit Project Description",
                            url: Utils.url('submission-submit-description'),
                            group: ['student']
                        },
                        {
                            name: "Manage Project Description",
                            url: Utils.url('submission-manage-description'),
                            group: ['student', 'supervisor','coordinator']
                        }
                    ]
                },
                {
                    name: "Project Plan",
                    url: '#',
                    icon: 'fa-file',
                    group: ['student', 'supervisor', 'coordinator'],
                    subShow: this.props.active1==='Project Plan',
                    sub: [
                        {
                            name: "Submit Project Plan",
                            url: Utils.url('submission-submit-plan'),
                            group: ['student']
                        },
                        {
                            name: "Manage Project Plan",
                            url: Utils.url('submission-manage-plan'),
                            group: ['student', 'supervisor', 'coordinator']
                        }
                    ]
                },
                {
                    name: "Project Report",
                    url: '#',
                    icon: 'fa-file',
                    group: groups,
                    subShow: this.props.active1==='Project Report',
                    sub: [
                        {
                            name: "Submit Project Report",
                            url: Utils.url('submission-submit-report'),
                            group: ['student']
                        },
                        {
                            name: "Manage Project Report",
                            url: Utils.url('submission-manage-report'),
                            group: groups
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
                            <a className={'subItem '+(active2===menu[i].sub[k].name?'active':'')} href={menu[i].sub[k].url} key={k}>
                                <div className="first hide"></div>
                                <div className="text">{menu[i].sub[k].name}</div>
                                <div className="fa fa-angle-down hide"></div>
                            </a>
                        );
                    }
                }
                element.push(
                    <div className="item" key={i}>
                        <div className={'content '+(active1===menu[i].name?'active':'')} onClick={menu[i].sub.length>0?this.showSubMenu.bind(this, i):this.jump.bind(this, menu[i].url)}>
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
        let menu = this.state.menu;
        menu[id].subShow = !this.state.menu[id].subShow;
        this.setState({
            menu: menu
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
                    <img src={require('../Asset/Img/logo.png')} alt="" className="logo" />
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
