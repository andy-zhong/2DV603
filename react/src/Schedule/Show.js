import React from 'react';

import Utils from '../utils';
import Config from '../config';
import './Show.css';

import Header from '../Public/Header'
import Sider from '../Public/Sider'

class Show extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Schedule';
        let $this = this;
        $this.state = {
            member: {groupObj: {}},
            list: []
        };
        Utils.checkMember(function(res){
            $this.setState({
                member: res
            });
            Utils.request(Config.requestUrl+Utils.requestUrl('schedule-list'), '', function(res){
                $this.setState({
                    list: res
                })
            })
        });
    }

    renderOperation(schedule){
        let type = this.state.member.groupObj.type;
        if(type==='coordinator')
            return <a href={Utils.url('schedule-edit-'+schedule.id)}>Edit</a>;
        if(type==='student'){
            if(schedule.type.toLowerCase().indexOf('description')>-1)
                return <a href={Utils.url('submission-submit-description')}>Go</a>;
            if(schedule.type.toLowerCase().indexOf('plan')>-1)
                return <a href={Utils.url('submission-submit-plan')}>Go</a>;
            if(schedule.type.toLowerCase().indexOf('report')>-1)
                return <a href={Utils.url('submission-submit-report')}>Go</a>;
        }
        return 'None';
    }

    render(){
        let member = this.state.member;
        return (
            <div>
                <Sider member={member} active1="Schedule" active2={null}></Sider>
                <Header title1="Schedule" title2={null}></Header>

                <div className="wrap">
                    <table className="table table-striped">
                        <thead>
                        <tr>
                            <th>Title</th>
                            <th>Type</th>
                            <th>Start Time</th>
                            <th>End Time</th>
                            <th>Operation</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.state.list.map((l, k)=>{
                            return <tr key={k}>
                                <td>{l.title}</td>
                                <td>{l.type}</td>
                                <td>{Utils.timeToDate(l.startTime)}</td>
                                <td>{Utils.timeToDate(l.endTime)}</td>
                                <td>{this.renderOperation(l)}</td>
                            </tr>
                        })}
                        </tbody>
                    </table>
                    {member.group===1?
                        <a className="btn btn-success" href={Utils.url('schedule-add')}>Add new schedule</a>
                    :null}
                </div>
            </div>
        )
    }
}

export default Show;
