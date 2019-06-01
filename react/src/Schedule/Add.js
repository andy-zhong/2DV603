import React from 'react';
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';

import Config from '../config';
import Utils from '../utils';

import Sider from "../Public/Sider";
import Header from "../Public/Header";

import './Edit.css';

class Add extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Schedule Add';
        let $this = this;
        $this.state = {
            member: {},
            schedule: {},
            id: props.match.params.id,
            type: ['SubmitDescription', 'SubmitPlan', 'SubmitReport']
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
            })
        })
        this.startTimeChange = this.startTimeChange.bind(this);
        this.endTimeChange = this.endTimeChange.bind(this);
    }

    input(e){
        e.preventDefault();
        let schedule = this.state.schedule;
        e = e.target;
        schedule[e.name] = e.value;
        this.setState({
            schedule: schedule
        });
    }

    startTimeChange(time){
        let schedule = this.state.schedule;
        schedule.startTime = time;
        this.setState({
            schedule: schedule
        })
    }

    endTimeChange(time){
        let schedule = this.state.schedule;
        schedule.endTime = time;
        this.setState({
            schedule: schedule
        })
    }

    submit(e){
        e.preventDefault();
        let postdata = Object.assign(this.state.schedule);
        postdata.startTime = new Date(postdata.startTime).getTime() / 1000;
        postdata.endTime = new Date(postdata.endTime).getTime() / 1000;
        postdata.cookie = Utils.getLoginCookie();
        Utils.request(Config.requestUrl+Utils.requestUrl('schedule-add'), Utils.jsonToForm(postdata), function(res){
            Utils.alert(res, function(){
                window.location.href = Utils.url('schedule-show');
            });
        }, function(msg){
            Utils.alert(msg);
        });
    }

    render(){
        let schedule = this.state.schedule;
        return (
            <div>
                <Sider member={this.state.member} active1="Schedule"></Sider>
                <Header title1={null} title2="Schedule"></Header>

                <div className="wrap schedule-edit">
                    <div className="item">
                        <div className="title">Title:</div>
                        <div className="value">
                            <input value={schedule.title||''} className="form-control" name="title" onChange={this.input.bind(this)} />
                        </div>
                    </div>
                    <div className="item">
                        <div className="title">Type:</div>
                        <div className="value">
                            <select className="form-control" value={schedule.type} name="type" onChange={this.input.bind(this)}>
                                {this.state.type.map((t, k)=>{
                                    return <option value={t} key={k}>{t}</option>
                                })}
                            </select>
                        </div>
                    </div>
                    <div className="item">
                        <div className="title">Start Time:</div>
                        <div className="value">
                            <DatePicker
                                className="form-control"
                                selected={schedule.startTime}
                                onChange={this.startTimeChange}
                                dateFormat="yyyy-MM-dd HH:mm"
                                timeFormat="HH:mm"
                                timeIntervals={1}
                                showTimeSelect
                                name="startTime"
                            />
                        </div>
                    </div>
                    <div className="item">
                        <div className="title">End Time:</div>
                        <div className="value">
                            <DatePicker
                                className="form-control"
                                selected={schedule.endTime}
                                onChange={this.endTimeChange}
                                dateFormat="yyyy-MM-dd HH:mm"
                                timeFormat="HH:mm"
                                timeIntervals={1}
                                showTimeSelect
                                name="endTime"
                            />
                        </div>
                    </div>
                    <button className="btn btn-success" onClick={this.submit.bind(this)}>Submit</button>
                </div>
            </div>
        )
    }
}

export default Add;
