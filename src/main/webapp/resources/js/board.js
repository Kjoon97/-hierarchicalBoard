let index ={
     init: function(){
         $("#btn-save").on("click",()=>{   //'글 작성' 버튼 클릭하면 해당 함수 호출 됨.
             this.save();
         });
         $("#delete-btn").eq(0).on("click",()=>{   //'글 삭제' 버튼 클릭하면 해당 함수 호출 됨.
        	 $('.modal-delete-bg').eq(0).toggleClass('show');
         });
         $("#modal-delete-close").eq(0).on("click",()=>{   //'글 삭제' 버튼 클릭하면 해당 함수 호출 됨.
        	 $('.modal-delete-bg').eq(0).toggleClass('show');
         });
         $("#modal-delete-submit").eq(0).on("click",()=>{   //'글 삭제' 버튼 클릭하면 해당 함수 호출 됨.
        	 this.delete();
         });
         $("#update-btn").eq(0).on("click",()=>{   //'글 수정' 버튼 클릭하면 해당 함수 호출 됨.
        	 this.update();
         });
         $("#pw-text1").hide(); $("#pw-text2").hide(); $("#pw-text3").hide(); $("#pw-text4").hide();
         $("#userId-text1").hide(); $("#userId-text2").hide();
         $("#content-text1").hide();
         $("#title-text1").hide(); $("#title-text2").hide(); 
         $("#deleteDate-text1").hide();
         $("#passwd").on('input',()=>{
        	this.passwdValidation(); 
         });
     },

     save: function(){
    	 
         let data = {
               title: $("#title").val(),
               content: $("#content").val(),
               userId: $("#userId").val(),
               passwd: $("#passwd").val(),
               deleteDate: $("#deleteDate").val() 
         };
         pw = data.passwd;
    	 var num = pw.search(/[0-9]/g);
    	 var eng = pw.search(/[a-z]/ig);
    	 var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
         if(!data.title || data.title.trim() === ""){
        	 $('#title-text1').show();
        	 $('#title-text2').hide();
        	 return false;
         } else if(data.title.length>100){
        	 $('#title-text1').hide();
        	 $('#title-text2').show();
        	 return false;
         } else if(!data.content || data.content.trim() === ""){
        	 $('#content-text1').show();
        	
        	 return false;
         } else if(!data.userId || data.userId.trim() === ""){
        	 $('#userId-text1').show();
        	 $('#userId-text2').hide();
        	 return false;
         } else if(data.userId.length>12){
        	 $('#userId-text2').show();
        	 $('#userId-text1').hide();
        	 
        	 return false;
         } else if(!data.deleteDate || data.deleteDate.trim() === ""){
        	 $('#deleteDate-text1').show();
        	 return false;
         } else if(!data.passwd || data.passwd.trim() === ""){
        	 $('#pw-text1').show();
        	 return false;
         } else if(data.passwd.length < 6 || data.passwd.length > 12){
    		 $('#pw-text2').show();
    		 $('#pw-text1').hide();
    		 $('#pw-text3').hide();
    		 $('#pw-text4').hide();
    		 
    		 return false;
    		 
    	 } else if(data.passwd.search(/\s/) != -1){
    		 $('#pw-text3').show();
    		 
    		 return false;
    		
    	 } else if(num < 0 || eng < 0 || spe < 0 ){
    		 $('#pw-text4').show();
    		 $('#pw-text1').hide();
    		 $('#pw-text2').hide();
    		 $('#pw-text3').hide();
    		 
    		 return false;
    	 }
         
         var today = new Date();
         var DeleteDateObj = new Date(data.deleteDate);
         
         if (DeleteDateObj < today) {
             alert("삭제일은 오늘 날짜보다 이후여야합니다.");
             return false;
         }
         
         console.log(data)
         //ajax 호출 default가 비동기 호출.
         $.ajax({
            type: "POST",
            url: "/api/board",      // 컨트롤러 /api/board 로 데이터 전송.
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
         }).done(function(resp){
            alert("작성이 완료 되었습니다.");
            location.href ="/";
         }).fail(function(error){
            alert(JSON.stringify(error));
         });
     },
     
     delete: function(){
              var id = $("#id").val();     //detail.html에서의 ${board.id} 값 가져옴.
              let data= {
            		  passwd: $("#passwd").val()
              }
              if(!data.passwd || data.passwd.trim() === ""){
             	 $('#pw-text1').show();
         		 $('#pw-text2').hide();
         		 $('#pw-text3').hide();
         		 $('#pw-text4').hide();
             	 return false;
              }
              console.log(data)
              //ajax 호출 default가 비동기 호출.
              console.log(id)
              $.ajax({
                 type: "PATCH",
                 url: "/api/board/"+id,
                 data: JSON.stringify(data),
                 contentType: "application/json; charset=utf-8",
                 dataType: "json"
              }).done(function(resp){
             	 if(resp.statusCode == 200){
                     alert("삭제가 완료 되었습니다.");
                     location.href ="/";
            	 }else{
            		 alert(resp.data)
            	 } 
              }).fail(function(error){
                 alert(JSON.stringify(error));
              });
     },
     
     update: function(){
         let id = $("#id").val();
         let data = {
               title: $("#title").val(),
               content: $("#content").val(),
               userId:$("#userId").val(),
               passwd: $("#passwd").val(),
               deleteDate : $("#deleteDate").val()
         };
         if(!data.title || data.title.trim() === ""){
        	 $('#title-text1').show();
        	 $('#title-text2').hide();
        	 return false;
         }else if(data.title.length>100){
        	 $('#title-text1').hide();
        	 $('#title-text2').show();
        	 return false;
         }
         else if(!data.content || data.content.trim() === ""){
        	 $('#content-text1').show();
        	 return false;
         }
         else if(!data.userId || data.userId.trim() === ""){
        	 $('#userId-text1').show();
        	 $('#userId-text2').hide();
        	 return false;
         } else if(data.userId.length>12){
        	 $('#userId-text2').show();
        	 $('#userId-text1').hide();
        	 
        	 return false;
         }
         else if(!data.deleteDate || data.deleteDate.trim() === ""){
        	 $('#deleteDate-text1').show();
        	 return false;
         } 
         else if(!data.passwd || data.passwd.trim() === ""){
        	 $('#pw-text1').show();
        	 return false;
         } 
         console.log(data)
         
         var today = new Date();
         var DeleteDateObj = new Date(data.deleteDate);
         
         if (DeleteDateObj < today) {
             alert("삭제일은 오늘 날짜보다 이후여야합니다.");
             return false;
         }
         
         //ajax 호출 default가 비동기 호출.
         $.ajax({
            type: "PUT",
            url: "/api/board/"+id,      // 컨트롤러 /api/board 로 데이터 전송.
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
         }).done(function(resp){
        	 if(resp.statusCode == 200){
        		 alert("수정이 완료되었습니다.")
        		 location.href ="/board/detail/"+id;
        	 }else{
        		 alert(resp.data)
        	 }
         }).fail(function(error){
            alert(JSON.stringify(error));
         });
     },
     
     passwdValidation: function(){
    	 var pw = $("#passwd").val();
    	 var num = pw.search(/[0-9]/g);
    	 var eng = pw.search(/[a-z]/ig);
    	 var spe = pw.search(/[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi);
    	 
    	 if(pw.length < 6 || pw.length > 12){
    		 $('#pw-text2').show();
    		 $('#pw-text1').hide();
    		 $('#pw-text3').hide();
    		 $('#pw-text4').hide();
    		 return false;
    		 
    	 }else if(pw.search(/\s/) != -1){
    		 $('#pw-text3').show();
    		 return false;
    		
    	 }else if(num < 0 || eng < 0 || spe < 0 ){
    		 $('#pw-text4').show();
    		 $('#pw-text1').hide();
    		 $('#pw-text2').hide();
    		 $('#pw-text3').hide();
    		 return false;
    	 }else {
    		 $('#pw-text1').hide();
    		 $('#pw-text2').hide();
    		 $('#pw-text3').hide();
    		 $('#pw-text4').hide();
    		 return true;
    	 }
     }

};

index.init();