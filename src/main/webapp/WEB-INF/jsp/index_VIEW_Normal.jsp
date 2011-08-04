<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>

<rs:resourceURL var="resourceURL" value="/rs/jquery/1.4.2/jquery-1.4.2.min.js"/>
<script type="text/javascript" src="${resourceURL}"></script>

<script type="text/javascript">
    	var notificationPortlet = notificationPortlet || {};
    	notificationPortlet.jQuery = jQuery.noConflict(true);

    	notificationPortlet.jQuery(function(){

       		var $ = notificationPortlet.jQuery;

			$(document).ready(function(){

	       		<c:forEach items="${serviceDataList}" var="serviceData">
	       			$.ajax({
	    				url: '<c:url value="/data"/>',
	    				data: "key=${serviceData.serviceKey}&namespace=<portlet:namespace/>",
	    				beforeSend: function(XMLHttpRequest) {
	    					$("#<portlet:namespace/>${serviceData.serviceKey}Data").html('<img src="<c:url value="/images/ajax-loader.gif"/>" width="220" height="19"/>');				
	    				},
	    				success: function(returnedHTMLResponse, textStatus) {
    						$("#<portlet:namespace/>${serviceData.serviceKey}Data").html(returnedHTMLResponse);
	    				},
	    				error: function(XMLHttpRequest, textStatus, errorThrown) {
	    					$("#<portlet:namespace/>${serviceData.serviceKey}Data").html(textStatus);
	    				}
	    			});
	       		
					$("#<portlet:namespace/>${serviceData.serviceKey}Button").live('click',function () { 
						$("#<portlet:namespace/>${serviceData.serviceKey}Detail").toggle(400);
						$(this).text($(this).text() == '(hide)' ? '(show)' : '(hide)');
						return false;
					});
				</c:forEach>
							
			});	
   		});
</script>
I am sample text put here to show that everything in this JSP file is rendering properly.

To be the new what is new or tasks portlet.
<div class="fl-widget portlet org-jasig-portlet-notice" role="section">

	<div class="fl-widget-content portlet-body" role="main">

		<c:forEach items="${serviceDataList}" var="serviceData">
			<div class="portlet-section" role="region">
	 			<h3 class="portlet-section-header" role="heading"><fmt:message key="service.${serviceData.serviceKey}.title"/></h3>
	 			<div class="portlet-section-body">
	 				<div id="<portlet:namespace/>${serviceData.serviceKey}Data">
					</div>
				</div>
			</div>
		</c:forEach>
		
	</div>
</div>
 