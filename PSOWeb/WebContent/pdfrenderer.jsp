<html>
<head>
<title>PDFObject example</title>
<script type="text/javascript" src="js/pdfobject.js"></script>
<script type="text/javascript">
	window.onload = function() {
		var success = new PDFObject({
			url : "confidentiality__agreement_example.pdf"
		}).embed();
	};
</script>
</head>
<body>
	<p>
		It appears you don't have Adobe Reader or PDF support in this web
		browser. <a href="sample.pdf">Click here to download the PDF</a>
	</p>

</body>
</html>