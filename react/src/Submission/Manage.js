import React from 'react';

import Utils from '../utils';
import Config from '../config';
import './Manage.css';

import Header from '../Public/Header'
import Sider from '../Public/Sider'

class Manage extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Manage Submission';
        let $this = this;
        let type = props.match.params.type;
        $this.state = {
            type: type,
            typeUpper: type.substring(0,1).toUpperCase() + type.substring(1),
            member: {groupObj: {}},
            list: []
        };
        Utils.checkMember(function(res){
            $this.setState({
                member: res
            });
            let postdata = {};
            postdata.cookie = Utils.getLoginCookie();
            postdata.type = $this.state.type;
            Utils.request(Config.requestUrl+Utils.requestUrl('submission-list'), Utils.jsonToForm(postdata), function(res){
                $this.setState({
                   list: res
                });
            })
        });
    }

    render(){
        let member = this.state.member;
        return (
            <div>
                <Sider member={member} active1={'Project '+this.state.typeUpper} active2={'Manage Project '+this.state.typeUpper}></Sider>
                <Header title1={'Project '+this.state.typeUpper} title2={'View Project '+this.state.typeUpper}></Header>

                <div className="wrap">
                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <th>Student ID</th>
                                <th>Document</th>
                                <th>Submit Time</th>
                                <th>Grade Time</th>
                                <th>Grade</th>
                                <th>Operation</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.list.map((l, k)=>{
                                return <tr key={k}>
                                    <td>{l.mid}</td>
                                    <td><a href={Config.requestUrl+Utils.requestUrl('submission-download')+'?cookie='+Utils.getLoginCookie()+'&id='+l.id}>Download</a></td>
                                    <td>{Utils.timeToDate(l.submitTime)}</td>
                                    <td>{Utils.timeToDate(l.gradeTime)}</td>
                                    <td>{Config.scores[l.score]}</td>
                                    <td><a href={Utils.url('submission-view-'+this.state.type)+'?id='+l.id}>View</a></td>
                                </tr>
                            })}
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default Manage;
