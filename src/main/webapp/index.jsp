<%--
  Created by IntelliJ IDEA.
  UserPrototype: UserPrototype
  Date: 7/11/2017
  Time: 2:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html >
<head>
  <meta charset="UTF-8">
  <title>Opening Sequence</title>
  <script src="https://s.codepen.io/assets/libs/modernizr.js" type="text/javascript"></script>

  <meta charset="UTF-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Text Opening Sequence with CSS Animations</title>
  <meta name="description" content="Text Opening Sequence with CSS Animations" />



  <link rel="stylesheet" href="style.css">


</head>

<body>
<div class="container">
  <div class="os-phrases" id="os-phrases">
    <h2>The amazing IMS0</h2>
  </div>
</div><!-- /container -->
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
<script src='https://cdnjs.cloudflare.com/ajax/libs/lettering.js/0.6.1/jquery.lettering.min.js'></script>

<script src="index.js"></script>

</body>
</html>

<script>
    $("#os-phrases > h2")
        .css('opacity', 1).lettering( 'words' )
        .children( "span" ).lettering()
        .children( "span" ).lettering();
</script>
