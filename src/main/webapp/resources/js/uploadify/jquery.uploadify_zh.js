var onSelectError = function (file, errorCode, errorMsg) {
    var msgText = "上传失败\n";
    switch (errorCode) {
        case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
            msgText += "每次最多上传 " + this.settings.queueSizeLimit + "个文件";
            break;
        case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
            msgText += "文件大小超过限制( " + this.settings.fileSizeLimit + " )";
            break;
        case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
            msgText += "文件大小为0";
            break;
        case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
            msgText += "文件格式不正确，仅限 " + this.settings.fileTypeExts;
            break;
        default:
            msgText += "错误代码：" + errorCode + "\n" + errorMsg;
    }
    alert(msgText);
};
var onUploadError = function (file, errorCode, errorMsg, errorString) {
    if (errorCode == SWFUpload.UPLOAD_ERROR.FILE_CANCELLED
        || errorCode == SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED) {
        return;
    }
    var msgText = "上传失败\n";
    switch (errorCode) {
        case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
            msgText += "HTTP 错误\n" + errorMsg;
            break;
        case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
            msgText += "上传文件丢失，请重新上传";
            break;
        case SWFUpload.UPLOAD_ERROR.IO_ERROR:
            msgText += "IO错误";
            break;
        case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
            msgText += "安全性错误\n" + errorMsg;
            break;
        case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
            msgText += "每次最多上传 " + this.settings.uploadLimit + "个";
            break;
        case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
            msgText += errorMsg;
            break;
        case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND:
            msgText += "找不到指定文件，请重新操作";
            break;
        case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
            msgText += "参数错误";
            break;
        default:
            msgText += "文件:" + file.name + "\n错误码:" + errorCode + "\n"
            + errorMsg + "\n" + errorString;
    }
    alert(msgText);
};

var onSelect = function (file) {
    // Load the swfupload settings
    var settings = this.settings;

    // Check if a file with the same name exists in the queue
    var queuedFile = {};
    for (var n in this.queueData.files) {
        queuedFile = this.queueData.files[n];
        if (queuedFile.uploaded != true && queuedFile.name == file.name) {
            var replaceQueueItem = confirm('The file named "' + file.name + '" is already in the queue.\nDo you want to replace the existing item in the queue?');
            if (!replaceQueueItem) {
                this.cancelUpload(file.id);
                this.queueData.filesCancelled++;
                return false;
            } else {
                $('#' + queuedFile.id).remove();
                this.cancelUpload(queuedFile.id);
                this.queueData.filesReplaced++;
            }
        }
    }

    // Get the size of the file
    var fileSize = Math.round(file.size / 1024);
    var suffix = 'KB';
    if (fileSize > 1000) {
        fileSize = Math.round(fileSize / 1000);
        suffix = 'MB';
    }
    var fileSizeParts = fileSize.toString().split('.');
    fileSize = fileSizeParts[0];
    if (fileSizeParts.length > 1) {
        fileSize += '.' + fileSizeParts[1].substr(0, 2);
    }
    fileSize += suffix;

    // Truncate the filename if it's too long
    var fileName = file.name;
    if (fileName.length > 25) {
        fileName = fileName.substr(0, 25) + '...';
    }

    // Create the file data object
    itemData = {
        'fileID': file.id,
        'instanceID': settings.id,
        'fileName': fileName,
        'fileSize': fileSize
    }

    // Create the file item template
    if (settings.itemTemplate == false) {
        settings.itemTemplate = '<div id="${fileID}" class="uploadify-queue-item">\
					<div class="cancel">\
						<a href="javascript:$(\'#${instanceID}\').uploadify(\'cancel\', \'${fileID}\')">X</a>\
					</div>\
					<span class="fileName">${fileName} (${fileSize})</span><span class="data"></span>\
					<div class="uploadify-progress">\
						<div class="uploadify-progress-bar"><!--Progress Bar--></div>\
					</div>\
				</div>';
    }

    // Run the default event handler
    if ($.inArray('onSelect', settings.overrideEvents) < 0) {

        // Replace the item data in the template
        itemHTML = settings.itemTemplate;
        for (var d in itemData) {
            itemHTML = itemHTML.replace(new RegExp('\\$\\{' + d + '\\}', 'g'), itemData[d]);
        }

        // Add the file item to the queue
        $('#' + settings.queueID).append(itemHTML);
    }

    this.queueData.queueSize += file.size;
    this.queueData.files[file.id] = file;

    // Call the user-defined event handler
    if (settings.onSelect) settings.onSelect.apply(this, arguments);
};
var onUploadStart = function (file) {
    // Load the swfupload settings
    var settings = this.settings;

    var timer = new Date();
    this.timer = timer.getTime();
    this.bytesLoaded = 0;
    if (this.queueData.uploadQueue.length == 0) {
        this.queueData.uploadSize = file.size;
    }
    if (settings.checkExisting) {
        $.ajax({
            type: 'POST',
            async: false,
            url: settings.checkExisting,
            data: {filename: file.name},
            success: function (data) {
                if (data == 1) {
                    var overwrite = confirm('A file with the name "' + file.name + '" already exists on the server.\nWould you like to replace the existing file?');
                    if (!overwrite) {
                        this.cancelUpload(file.id);
                        $('#' + file.id).remove();
                        if (this.queueData.uploadQueue.length > 0 && this.queueData.queueLength > 0) {
                            if (this.queueData.uploadQueue[0] == '*') {
                                this.startUpload();
                            } else {
                                this.startUpload(this.queueData.uploadQueue.shift());
                            }
                        }
                    }
                }
            }
        });
    }

    // Call the user-defined event handler
    if (settings.onUploadStart) settings.onUploadStart.call(this, file);
};