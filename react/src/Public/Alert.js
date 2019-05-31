import React from 'react';
import Modal from "react-bootstrap/Modal";

class Alert extends React.Component {

    constructor(props){
        super(props);
        this.state = {
            smShow: true
        };
        this.smClose = this.smClose.bind(this);
    }

    smClose(){
        this.setState({ smShow: false });
        if(this.props.callback){
            this.props.callback();
        }
    }

    render(){

        return (
            <div>
                <Modal
                    size="sm"
                    show={this.state.smShow}
                    onHide={this.smClose}
                    aria-labelledby="alert"
                    style={{color:'#000'}}
                >
                    <Modal.Header closeButton>
                        <Modal.Title id="alert">
                            Tip
                        </Modal.Title>
                    </Modal.Header>
                    <Modal.Body>{this.props.message}</Modal.Body>
                </Modal>
            </div>
        )
    }
}

export default Alert;
