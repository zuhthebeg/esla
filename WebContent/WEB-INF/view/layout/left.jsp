<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h2>Documentation</h2>

<script type="text/javascript">
  $(function(){

    // --- Initialize sample trees
    $("#tree").dynatree({
    	/*
    	dnd: {
            onDragStart: function(node) {
                logMsg("tree.onDragStart(%o)", node);
                return true;
            },
            onDrop: function(node, sourceNode, hitMode, ui, draggable) {
                logMsg("tree.onDrop(%o, %o, %s)", node, sourceNode, hitMode);
                sourceNode.move(node, hitMode);
            }
        },
        */
      title: "Lazy loading sample",
      fx: { height: "toggle", duration: 200 },
      autoFocus: true, // Set focus to first child, when expanding or lazy-loading.
      initAjax: {
        url: "/esla/tree/init.json"
        },

      onActivate: function(node) {
    	  switch(node.getLevel()){
    	  case 1: 
    	  case 2:
    	  case 3:
    	  case 4:
    	  case 5:
    		  nodeManager(node.data.key, node.getLevel());
    		  break;
    	  case 6:
    		  nodeManagerAndPageList(node.data.key, node.getLevel());
    		  break;
    	  default:
    		  break;
    		  
    	  }
        $("#echoActive").text("" + node.data.name + " (" + node.data.key+ ")");
      },

      onLazyRead: function(node){
        node.appendAjax({
          url: "/esla/tree/child/"+node.data.key+".json",
          debugLazyDelay: 200
        });
      }
    });
    $("#btnReloadActive").click(function(){
      var node = $("#tree").dynatree("getActiveNode");
      if( node && node.isLazy() ){
        node.reloadChildren(function(node, isOk){
        });
      }else{
        alert("Please activate a lazy node first.");
      }
     });
    $("#btnLoadKeyPath").click(function(){
      var tree = $("#tree").dynatree("getTree");
      tree.loadKeyPath(node.getKeyPath(), function(node, status){
        if(status == "loaded") {
          // 'node' is a parent that was just traversed.
          // If we call expand() here, then all nodes will be expanded
          // as we go
          alert(node.data.key);
          node.expand();
        }else if(status == "ok") {
          node.activate();
        }
      });
     });
   
  });
  function editNode(node){
	  var prevTitle = node.data.title,
	    tree = node.tree;
	  // Disable dynatree mouse- and key handling
	  tree.$widget.unbind();
	  // Replace node with <input>
	  $(".dynatree-title", node.span).html("<input id='editNode' value='" + prevTitle + "'>");
	  // Focus <input> and bind keyboard handler
	  $("input#editNode")
	    .focus()
	    .keydown(function(event){
	      switch( event.which ) {
	      case 27: // [esc]
	        // discard changes on [esc]
	        $("input#editNode").val(prevTitle);
	        $(this).blur();
	        break;
	      case 13: // [enter]
	        // simulate blur to accept new value
	        $(this).blur();
	        break;
	      }
	    }).blur(function(event){
	      // Accept new value, when user leaves <input>
	      var title = $("input#editNode").val();
	      node.setTitle(title);
	      // Re-enable mouse and keyboard handlling
	      tree.$widget.bind();
	      node.focus();
	      updateNodeInfo(node, title);
	    });
	}
	// ----------
	$(function(){
	  var isMac = /Mac/.test(navigator.platform);
	  $("#tree").dynatree({
	    title: "Event samples",
	    onClick: function(node, event) {
	      if( event.shiftKey ){
	        editNode(node);
	        return false;
	      }
	    },
	    onDblClick: function(node, event) {
	      editNode(node);
	      return false;
	    },
	    onKeydown: function(node, event) {
	      switch( event.which ) {
	      case 113: // [F2]
	        editNode(node);
	        return false;
	      case 13: // [enter]
	        if( isMac ){
	          editNode(node);
	          return false;
	        }
	      }
	    }
	  });
	});
  function nodeManager(idx, level){	  
	 $.ajax({
		   type:'post',
		   url:'/esla/tree/manage',
		   data: ({idx:idx, level:level}),
		   success:function(data){
			   if(data.trim() == "")
				   alert("error");
			   else{
				   $('#content').html(data);
				   $('#doc_idx').val(idx);
			   }
		   }
	});
  }
  function nodeManagerAndPageList(idx, level){
		 $.ajax({
			   type:'post',
			   url:'/esla/tree/manage',
			   data: ({idx:idx, level:level}),
			   success:function(data){
				   if(data.trim() == "")
					   alert("error");
				   else{
					   $('#content').html(data);
						 $.ajax({
							   type:'post',
							   url:'/esla/getPage',
							   data: ({idx:idx}),
							   success:function(data){
								   if(data.trim() == "")
									   alert("페이지가 없습니다.");
								   else{
									   $('#pageList').html(data);
									   $('#doc_idx').val(idx);
								   }
							   }
						});	
				   }
			   }
		});
}
  function updateNodeInfo(node, val){
	var meta_idx = node.data.key;
	var meta_lev = node.getLevel();
	var meta_val = val;
	 $.ajax({
		   type:'post',
		   url:'/esla/tree/update.json',
		   data: ({idx:meta_idx, val : meta_val, level : meta_lev }),
		   success:function(data){
			   if(data.status == "success"){				   
				   nodeManager(meta_idx, meta_lev);
			   }else
				   alert("수정실패");
		   }
		});
	}
	function changeChart(type){
		var doc_idx = $('#doc_idx').val();
		 $.ajax({
			   type:'post',
			   url:'/esla/statistics/'+type,
			   data: ({doc_idx:doc_idx}),
			   success:function(data){
				   //$('.mw_layer').addClass('open');
				   $('#content').html(data);
			   }
		});	
	}
	function loadPageStatistics(){
		var doc_idx = $('#doc_idx').val();
		if(doc_idx){
			selectPart();
		}else
			alert('범위를 선택바랍니다.');
		
	}

/* 	function changePart(part){
		var doc_idx = $('#doc_idx').val();
		var isAllWord = $('#isAllWord').is(":checked");
		 $.ajax({
			   type:'post',
			   url:'/esla/statistics/bar',
			   data: ({doc_idx:doc_idx, part : part, isAllWord : isAllWord}),
			   success:function(data){
				   //$('.mw_layer').addClass('open');
				   $('#content').html(data); 
				   if(isAllWord)
						$('.word_checkbox').attr("checked", "checked");
			   }
		});	
	} */
	
	function selectPart(){
		var doc_idx = $('#doc_idx').val();
		/* var isAllWord = $('#isAllWord').is(":checked"); */
		
		var bigPart = $('#bigPart').is(":checked") ? $('#bigPart').val() : "";
		var smallPart=$('#smallPart').is(":checked") ? $('#smallPart').val() : "";
		var pagePart=$('#pagePart').is(":checked") ? $('#pagePart').val() : "";
		var contentPart=$('#contentPart').is(":checked") ? $('#contentPart').val() : "";
		var actionPart=$('#actionPart').is(":checked") ? $('#actionPart').val() : "";
		var problemPart=$('#problemPart').is(":checked") ? $('#problemPart').val() : "";
			
		 $.ajax({
			   type:'post',
			   url:'/esla/statistics/bar',
			   data: ({doc_idx:doc_idx, 
					bigPart:bigPart,
					smallPart:smallPart,
					pagePart:pagePart,
					contentPart:contentPart,
					actionPart:actionPart,
					problemPart:problemPart,
				   /* isAllWord : isAllWord */
				   }),
			   success:function(data){
				   //$('.mw_layer').addClass('open');
				   $('#content').html(data); 
				   /* if(isAllWord)
						$('.word_checkbox').attr("checked", "checked"); */
			   }
		});	
	}
	
	function saveToExcelAsChapter(){
		var doc_idx = $('#doc_idx').val();
		window.open("/esla/chapter/excel/"+doc_idx);
	}
	
	function saveToExcelByCondition(){
		
		var isAllWord = 'false';
		var doc_idx = $('#doc_idx').val();
		
		var st_phy = $('#st_phy').is(":checked") ? 1 : 0; 
		var st_ear = $('#st_ear').is(":checked") ? 1 : 0; 
		var st_che = $('#st_che').is(":checked") ? 1 : 0; 
		var st_bio = $('#st_bio').is(":checked") ? 1 : 0; 
		
		var ex_phy = $('#ex_phy').is(":checked") ? 1 : 0; 
		var ex_ear = $('#ex_ear').is(":checked") ? 1 : 0; 
		var ex_che = $('#ex_che').is(":checked") ? 1 : 0; 
		var ex_bio = $('#ex_bio').is(":checked") ? 1 : 0; 

		var check = st_phy+st_ear+st_che+st_bio+ex_phy+ex_ear+ex_che+ex_bio;
		if(check == 0){ isAllWord = 'true'; }//alert('추출할 용어집을 선택하세요.'); return false;}
		
		var bigPart = $('#bigPart').is(":checked") ? 1 : "";
		var smallPart=$('#smallPart').is(":checked") ? 1 : "";
		var pagePart=$('#pagePart').is(":checked") ? 1 : "";
		var contentPart=$('#contentPart').is(":checked") ? 1 : "";
		var actionPart=$('#actionPart').is(":checked") ? 1 : "";
		var problemPart=$('#problemPart').is(":checked") ? 1 : "";
		
		var url = '/esla/statistics/save?'+
		"isAllWord="+isAllWord+
		"&doc_idx="+doc_idx+
		"&st_phy="+st_phy+
		"&st_ear="+st_ear+
		"&st_che="+st_che+
		"&st_bio="+st_bio+
		"&ex_phy="+ex_phy+
		"&ex_ear="+ex_ear+
		"&ex_che="+ex_che+
		"&ex_bio="+ex_bio+
		
		"&bigPart="+bigPart+
		"&smallPart="+smallPart+
		"&pagePart="+pagePart+
		"&contentPart="+contentPart+
		"&actionPart="+actionPart+
		"&problemPart="+problemPart;
		window.open(url);
		/* $.post(
			   '/esla/statistics/save',
			   {doc_idx:doc_idx, part :part,
				   st_phy:st_phy,st_ear:st_ear,st_che:st_che,st_bio:st_bio,
				   ex_phy:ex_phy,ex_ear:ex_ear,ex_che:ex_che,ex_bio:ex_bio},
			   function(data){
					var child = window.open();	
					child.document.write(data);
			   }
		);	  */
		
//		window.open("/esla/chapter/excel/"+doc_idx);
	}
	
	function loadWordSearch(){
		var doc_idx = $('#doc_idx').val();
		if(doc_idx){
			 $.ajax({
				   type:'post',
				   data: ({doc_idx:doc_idx}),
				   url:'/esla/statistics/search',
				   success:function(data){
					   //$('.mw_layer').addClass('open');
					   $('#content').html(data); 
				   }
			});				
		}else{
			alert('범위를 선택바랍니다.');
		}
	}
</script>
<input type="hidden" name="doc_idx" id="doc_idx">
<div id="tree" style="height: 650px;"> </div>
<br/>
<div>메타정보 : <span id="echoActive">-</span></div>
<br/>
<span class="button black large"><a href="#" onclick="loadPageStatistics()">빈도분석</a></span>
<span class="button black large"><a href="#" onclick="loadWordSearch()">용례추출</a></span>
<!-- <span class="button black large"><a href="#" onclick="saveToExcelAsChapter()">파일 저장</a></span> -->