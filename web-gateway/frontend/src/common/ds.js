/**
 * author:fulin.wang
 * date:2017-01-09
 * remark:ajax
 */
import $ from 'jquery2'

export default  {
	GET: function(params, success, error){
		$.ajax({
	        url: params.url,
	        type: 'GET',
	        data: params.data,
	        dataType: 'json', //返回的数据格式：json/xml/html/script/jsonp/text
	        beforeSend: function(xhr) {

	        },
	        success: function(data, textStatus, jqXHR) {
	            success & success(data)
	        },
	        error: function(xhr, textStatus) {
	        	error && error(textStatus)
	        },
	        complete: function() {

	        }
	    })
	},
	POST: function(params, success, error){
		$.ajax({
	        url: params.url,
	        type: 'POST',
	        data: JSON.stringify(params.data),
	        dataType: 'json', //返回的数据格式：json/xml/html/script/jsonp/text			
			contentType: 'application/json',
	        beforeSend: function(xhr) {

	        },
	        success: function(data, textStatus, jqXHR) {
	            success & success(data)
	        },
	        error: function(xhr, textStatus) {
	        	error & error(data)
	        },
	        complete: function() {

	        }
	    })
	},
	PUT: function(params, success, error){
		$.ajax({
	        url: params.url,
	        type: 'PUT',
	        data: JSON.stringify(params.data),
	        dataType: 'json', //返回的数据格式：json/xml/html/script/jsonp/text			
			contentType: 'application/json',
	        beforeSend: function(xhr) {

	        },
	        success: function(data, textStatus, jqXHR) {
	            success & success(data)
	        },
	        error: function(xhr, textStatus) {
	        	error & error(data)
	        },
	        complete: function() {

	        }
	    })
	},
	DELETE: function(params, success, error){
		$.ajax({
	        url: params.url,
	        type: 'DELETE',	       
	        dataType: 'json', //返回的数据格式：json/xml/html/script/jsonp/text			
			contentType: 'application/json',
	        beforeSend: function(xhr) {

	        },
	        success: function(data, textStatus, jqXHR) {
	            success & success(data)
	        },
	        error: function(xhr, textStatus) {
	        	error & error(data)
	        },
	        complete: function() {

	        }
	    })
	}
}