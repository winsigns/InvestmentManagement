/**
 * author:fulin.wang
 * date:2017-03-09
 * remark:ajax
 */
import $ from 'jquery2'
import lang from "./lang.js"

export default  {
	GET: function(params, success, error){			
		$.ajax({
	        url: params.url,
            headers:{
                'accept-language': lang.readLanguage()
			},
	        type: 'GET',
	        data: params.data,
	        dataType: 'json', //返回的数据格式：json/xml/html/script/jsonp/text
	        beforeSend: function(xhr) {
			
	        },
	        success: function(data, textStatus, jqXHR) {
	            success & success(data)
	        },
	        error: function(xhr, textStatus) {
				if (error && typeof error == 'function'){
					error && error(textStatus)
				} else{					
					if (params.v){
						if (params.v.loading){params.v.loading =false}
						params.v.$message({
							message: '获取数据失败',
							type: 'error'
						}); 
					}	
				}
	        },
	        complete: function() {

	        }
	    })
	},
	POST: function(params, success, error){
		$.ajax({
	        url: params.url,
	        type: 'POST',
            headers:{
                'accept-language': lang.readLanguage()
            },
	        data: JSON.stringify(params.data),
	        dataType: 'json', //返回的数据格式：json/xml/html/script/jsonp/text			
			contentType: 'application/json',
	        beforeSend: function(xhr) {

	        },
	        success: function(data, textStatus, jqXHR) {
	            success & success(data)
	        },
	        error: function(xhr, textStatus) {				
	        	if (error && typeof error == 'function'){
					error && error(textStatus);					
				} else{										
					if (params.v){
						if (params.v.loading){params.v.loading =false}
						params.v.$message({
							message: '提交数据失败',
							type: 'error'
						}); 
					}	
				}
	        },
	        complete: function() {

	        }
	    })
	},
	PUT: function(params, success, error){
		$.ajax({
	        url: params.url,
	        type: 'PUT',
            headers:{
                'accept-language': lang.readLanguage()
            },
	        data: JSON.stringify(params.data),
	        dataType: 'json', //返回的数据格式：json/xml/html/script/jsonp/text			
			contentType: 'application/json',
	        beforeSend: function(xhr) {

	        },
	        success: function(data, textStatus, jqXHR) {
	            success & success(data)
	        },
	        error: function(xhr, textStatus) {
	        	if (params.v){
					if (params.v.loading){params.v.loading =false}
					params.v.$message({
						message: '更新数据失败',
						type: 'error'
					}); 
				}	
	        },
	        complete: function() {

	        }
	    })
	},
	DELETE: function(params, success, error){
		$.ajax({
	        url: params.url,
	        type: 'DELETE',
            headers:{
                'accept-language': lang.readLanguage()
            },
	        dataType: 'json', //返回的数据格式：json/xml/html/script/jsonp/text			
			contentType: 'application/json',
	        beforeSend: function(xhr) {

	        },
	        success: function(data, textStatus, jqXHR) {
	            success & success(data)
	        },
	        error: function(xhr, textStatus) {
	        	if (params.v){
					if (params.v.loading){params.v.loading =false}
					params.v.$message({
						message: '删除数据失败',
						type: 'error'
					}); 
				}	
	        },
	        complete: function() {

	        }
	    })
	}
}