<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format">

	<xsl:output method="xml" encoding="utf-8" />

	<xsl:template match="/">
		<fo:root font-family="Arial">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="A4"
					page-width="210mm" page-height="297mm" margin-top="1cm"
					margin-bottom="1cm" margin-left="1.5cm" margin-right="1.5cm">
					<fo:region-body margin="2cm" />
				</fo:simple-page-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="A4">
				<fo:flow flow-name="xsl-region-body">
					<xsl:apply-templates />
					<fo:block/>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>

	<xsl:template match="product">
		<fo:block-container space-before.minimum="0.5em"
			page-break-inside="avoid">
			<fo:block>
				<xsl:text>Produkt nr </xsl:text>
				<xsl:value-of select="@id" />
				<xsl:text>.</xsl:text>
			</fo:block>
			<fo:block-container margin="1em"
				border-style="solid" border-width="2.5pt" padding="3pt"
				border-color="#2233AA">
				<fo:block font-weight="bold" font-size="14pt"
					margin-bottom="1em" color="#FF2244">
					<xsl:apply-templates select="name" />
				</fo:block>
				<fo:block font-weight="bold" color="green">
					<xsl:text>Cena: </xsl:text>
					<xsl:value-of select="price" />
				</fo:block>
				<fo:block color="green">
					<xsl:text>VAT: </xsl:text>
					<fo:inline font-style="italic">
					<xsl:value-of select="vat * 100" />
					<xsl:text>%</xsl:text>
					</fo:inline>
				</fo:block>
				<fo:block font-size="12pt" margin-bottom="1em">
					<xsl:apply-templates select="description" />
				</fo:block>
			</fo:block-container>
		</fo:block-container>
	</xsl:template>

</xsl:stylesheet>
