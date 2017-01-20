<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!-- <xsl:output method="html" indent="yes" /> -->
	<xsl:template match="/">
		<!-- TODO: Auto-generated template -->
		<html>
		<head>
			<title><xsl:value-of select="view/header/title"/></title>
		</head>
		<body>
			<xsl:variable name="name" select="view/body/form/name"/>
			<xsl:variable name="action" select="view/body/form/action"/>
			<xsl:variable name="method" select="view/body/form/method"/>
			<form name = "{$name}"  action="{$action}" method="{$method}">
				
				<xsl:for-each select="view/body/form/textView">
				<tr>
					
					<xsl:value-of select="label"/>
					<input type = "text" >
						<xsl:attribute name="name">
  							<xsl:value-of select="name" />
						</xsl:attribute>
						<xsl:attribute name="value">
  							<xsl:value-of select="value" />
						</xsl:attribute>
					</input>
				</tr>
				</xsl:for-each>
				<tr>
				<input type = "submit" >
					<xsl:attribute name="name">
  						<xsl:value-of select="view/body/form/buttonView/name" />
					</xsl:attribute>
					<xsl:attribute name="value">
  						<xsl:value-of select="view/body/form/buttonView/label" />
					</xsl:attribute>
					
				</input>
				</tr>
				
			</form>
			 
		</body>
		</html>
	</xsl:template>
</xsl:stylesheet>