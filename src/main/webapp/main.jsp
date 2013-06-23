<!doctype html>

<html lang="en">
<head>
<meta charset="utf-8" />
<title>Entity Tracking</title>


<link rel="stylesheet" media="all"
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/themes/smoothness/jquery-ui.css" />
<script  src="https://www.google.com/jsapi"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.min.js"></script>


<script src="jquery.sortElements.js"></script>
<style type="text/css">
@media print {
	.noprint {
		display: none;
	}
}

body {
	font-family: "Trebuchet MS", "Helvetica", "Arial", "Verdana",
		"sans-serif";
	font-size: 80%;
}

.bold {
	font-weight: bold;
}

.invisible {
	visibility: hidden;
}

.invisibleCompact {
	display: none;
}

.links {
	color: blue;
	padding-bottom: 30px;
}

.link {
	float: left;
	padding-right: 20px;
	cursor: pointer;
}


#main {
	clear: both;
}

#nonBanner {
	margin-left: 5%;
	margin-top: 2%;
}

#banner {
	margin-left: -8px;
	margin-top: -8px;
}

#help {
	padding-top: 0.5em;
	padding-bottom: 0.5em;
	padding-left: 1em;
	padding-right: 1em;
	font-size: 80%;
	background-color: rgb(240, 240, 240);
	width: 50em;
	max-width: 85%;
	margin-bottom: 0.5em;
	border: thin solid;
	border-color: gray;
}


.help {
	font-size: 62.5%;
	margin-left: 2em;
}



.no-close .ui-dialog-titlebar-close {
	display: none;
}
</style>
<script>

  // Load the Visualization API and the piechart package.
  google.load('visualization', '1.0', {'packages':['corechart']});
  //Set a callback to run when the Google Visualization API is loaded.
  google.setOnLoadCallback(function () {console.log('visualization api loaded');});

  $(function() {
	  
	$.ajaxSetup ({  
	    cache: false  
	});

	
	
  });

  </script>
</head>
<body>

	<div class="ui-widget">

		<div id="banner" class="noprint">
			<img src="image/banner.jpg" />
		</div>
		<div id="nonBanner">
			<div class="links noprint">
				<div id="trackingLink" class="link bold">Tracking</div>
				<div id="aboutLink" class="link">About</div>
			</div>

			<div id="about" class="invisibleCompact">
				<p id="version">Version APPLICATION_VERSION created by Dave Moten 2013 using JQuery, Java and Google
					AppEngine.</p>
				<p>
					Source code is <a href="https://github.com/davidmoten/entity-tracking">here</a>.
				</p>
				<p>
					Report any problems/requests <a
						href="https://github.com/davidmoten/entity-tracking/issues">here</a>.
				</p>
			</div>

			<div id="settings" class="invisibleCompact">
			</div>
		</div>
	</div>

</body>
</html>
