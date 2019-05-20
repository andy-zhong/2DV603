import React from 'react';

import Utils from '../utils';
import Config from '../config';
import './Submit.css';

import Header from '../Public/Header'
import Sider from '../Public/Sider'

class Submit extends React.Component {

    constructor(props){
        super(props);
        document.title = 'Description Submit';
        let $this = this;
        $this.state = {
            file: null,
            member: {groupObj: {}}
        };
        Utils.checkMember(function(res){
            $this.setState({
                member: res
            });
        });
        this.fileSelect = this.fileSelect.bind(this);
        this.submit = this.submit.bind(this);
    }

    submit() {
        if(window.confirm('Confirm to submit?')){
            const formData = new FormData();
            formData.append('file', this.state.file);
            formData.append('type', 'description');
            formData.append('member', Utils.getLoginCookie());
            formData.append('sid', '');
            Utils.upload(Config.requestUrl+'submit', formData, function(res){
                alert(res);
                window.location.reload();
            }, function(err){
                alert(err);
            });
        }
    }

    fileSelect(e) {
        let file = e.target.files[0];
        this.setState({
            file: file
        });
    }

    render(){
        let member = this.state.member;
        return (
            <div>
                <Sider member={member} active1="Project Description" active2="Submit Description"></Sider>
                <Header title1="Project Description" title2="Submit Description"></Header>

                <div className="wrap">

                    <table className="table table-striped">
                        <thead>
                        <tr>
                            <th>Submission</th>
                            <th>Project description</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Submission status</td>
                            <td>Submitted for grading/Not submit</td>
                        </tr>
                        <tr>
                            <td>Deadline</td>
                            <td>日期时间</td>
                        </tr>
                        <tr>
                            <td>Last modified</td>
                            <td>记录最后一次提交的时间</td>
                        </tr>
                        <tr>
                            <td>Grading status</td>
                            <td>分数／Not graded</td>
                        </tr>
                        </tbody>
                    </table>

                    <input type="file" className="form-control" placeholder="Choose your file" name="file" onChange={this.fileSelect} />
                    <button className="btn btn-primary" onClick={this.submit}>Submit</button>
                </div>
            </div>
        )
    }
}

export default Submit;
