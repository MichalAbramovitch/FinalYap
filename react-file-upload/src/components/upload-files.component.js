import React, { Component } from "react";
import UploadService from "../services/upload-files.service";
import LoadingSpinner from './LoadingSpinner';
import "./upload-files.css";

export default class UploadFiles extends Component {
  constructor(props) {
	super(props);
	this.selectFile = this.selectFile.bind(this);
	this.upload = this.upload.bind(this);
	this.state = {
		selectedFiles: undefined,
		currentFile: undefined,
		progress: 0,
		message: "",
		fileInfos: [],
		loading: false
	  };
	
  	}
	selectFile(event) {
	this.setState({
	selectedFiles: event.target.files
	});
	}
	upload() {
		let currentFile = this.state.selectedFiles[0];

		this.setState({
		progress: 0,
		currentFile: currentFile,
		loading : true
		});

		UploadService.upload(currentFile, (event) => {
		this.setState({
			progress: Math.round((100 * event.loaded) / event.total)
		});
		})
		.then((response) => {
			this.setState({
			message: response.data.message,
			});
			return UploadService.getFiles();
		})
		.then((files) => {
			this.setState({
			fileInfos: files.data,
			loading :false
			});
		})
		.catch(() => {
			this.setState({
			progress: 0,
			message: "Could not upload the file!",
			currentFile: undefined,
			});
		});

		this.setState({
		selectedFiles: undefined,
		});
	}
	componentDidMount() {
		UploadService.getFiles().then((response) => {
		this.setState({
			fileInfos: response.data,
		});
		});}


    render() {
		const {
		  selectedFiles,
		  currentFile,
		  progress,
		  message,
		  fileInfos,
		  loading
		} = this.state;
	
		return (
		  <div className="upload-file-wrapper">
			{currentFile && (
			  <div className="progress">
				<div
				  className="progress-bar progress-bar-info progress-bar-striped"
				  role="progressbar"
				  aria-valuenow={progress}
				  aria-valuemin="0"
				  aria-valuemax="100"
				  style={{ width: progress + "%" }}
				>
				  {progress}%
				</div>
			  </div>
			)}
			<div className="action-wrapper">
				<input type="file" onChange={this.selectFile} />
		
				<button className="btn btn-success"
				disabled={!selectedFiles}
				onClick={this.upload}
				>
				Upload
				</button>
			</div>
	
			

			<div className='process-data'> {loading? <LoadingSpinner /> :fileInfos > 0 && 'File is ready'}</div>
			<div className="card">
			  <div className="card-header">List of Files</div>
			  <ul className="list-group list-group-flush">
				{fileInfos &&
				  fileInfos.map((file, index) => (
					<li className="list-group-item" key={index}>
					  <a href={file.url}>{file.name}</a>
					</li>
				  ))}
			  </ul>
			</div>
		  </div>
		);
	  }
}