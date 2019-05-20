import React from 'react';
import Utils from '../utils';
import Config from '../config';
import './List.css';

import Header from '../Public/Header'
import Sider from '../Public/Sider'

class List extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Reader List';
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
                <Sider member={member} active1="Manage Member" active2="Reader List"></Sider>
                <Header title1="Manage Member" title2="Reader List"></Header>

                <div className="wrap">

                    <table className="table table-hover">
                        <thead>
                        <tr>
                            <th>Report title</th>
                            <th>Student name</th>
                            <th>Supervisor name</th>
                            <th>Choice</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Summer vibe</td>
                            <td>Meow</td>
                            <td>Illidan Stormrage</td>
                            <td><button className="button" ><span>Download </span></button></td>
                        </tr>
                        <tr>
                            <td>a</td>
                            <td>b</td>
                            <td>c</td>
                            <td><button className="button" ><span>Download </span></button></td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>2</td>
                            <td>3</td>
                            <td><button className="button" ><span>Download </span></button></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        )
    }
}

export default List;