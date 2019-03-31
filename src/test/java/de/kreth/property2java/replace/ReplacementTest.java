package de.kreth.property2java.replace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.kreth.property2java.replace.Replacement;

class ReplacementTest {

	private Replacement captionArticles;

	private Replacement labelLogout;

	private Replacement captionUserDetails;

	@BeforeEach
	void initTestItem() {
		captionArticles = new Replacement("de.kreth.clubinvoice", "Application_Properties", "CAPTION_ARTICLES",
				"CAPTION1_ARTICLES");
		captionUserDetails = new Replacement("de.kreth.clubinvoice", "Application_Properties",
				"CAPTION_USER_DETAILS",
				"CAPTION1_USER_DETAILS");
		labelLogout = new Replacement("de.kreth.clubinvoice", "Application_Properties", "LABEL_LOGOUT",
				"LABEL1_LOGOUT");
	}

	@Test
	void testReplaceStaticImported() {

		String exampleSourceCode = getExampleSourceCode();
		String result = captionArticles.replaceOccurrences(exampleSourceCode);
		assertNotNull(result);

		Optional<String> firstLine = result.lines()
				.filter(line -> line.contains("de.kreth.clubinvoice.Application_Properties")).findFirst();
		assertTrue(firstLine.isPresent());

		String captionArticlesLine = firstLine.get();

		assertEquals("import static de.kreth.clubinvoice.Application_Properties.CAPTION1_ARTICLES;",
				captionArticlesLine.trim());

		firstLine = result.lines().filter(l -> l.contains("new Button(CAPTION1_ARTICLES.getString(")).findFirst();
		assertTrue(firstLine.isPresent(), "CAPTION1_ARTICLES usage not found");

	}

	@Test
	void testReplaceClassImport() {

		String exampleSourceCode = getExampleSourceCode();
		String result = labelLogout.replaceOccurrences(exampleSourceCode);

		Optional<String> line = result.lines()
				.filter(l -> l.contains(".getString(Application_Properties.LABEL1_LOGOUT"))
				.findFirst();
		assertTrue(line.isPresent());

	}

	@Test
	void testReplaceUnimportedOccurrence() {

		String exampleSourceCode = getExampleSourceCode();
		String result = captionUserDetails.replaceOccurrences(exampleSourceCode);
		Optional<String> line = result.lines()
				.filter(l -> l.contains("new Button(de.kreth.clubinvoice.Application_Properties")
						&& l.contains("_USER_DETAILS"))
				.findFirst();
		assertTrue(line.isPresent());

		assertTrue(line.get().contains("de.kreth.clubinvoice.Application_Properties.CAPTION1_USER_DETAILS"),
				"absoulute class field CAPTION1_USER_DETAILS not found in : " + line.get());
	}

	@Test
	void testReplaceUnimportedOccurrenceWithLinebreak() {

		String exampleSourceCode = getExampleSourceCode();
		String result = captionUserDetails.replaceOccurrences(exampleSourceCode);
		List<String> lines = result.lines()
				.filter(l -> l.contains(".Application_Properties")
						&& l.contains("_USER_DETAILS"))
				.collect(Collectors.toList());
		assertEquals(2, lines.size());
//
//		assertTrue(line.get().contains("de.kreth.clubinvoice.Application_Properties.CAPTION1_USER_DETAILS"),
//				"absoulute class field CAPTION1_USER_DETAILS not found in : " + line.get());
	}

	String getExampleSourceCode() {
		return "package de.kreth.clubinvoice.ui;\r\n" +
				"\r\n" +
				"import static de.kreth.clubinvoice.Application_Properties.CAPTION_ARTICLES;\r\n" +
				"import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICEITEM_ADD;\r\n" +
				"" +
				"import static de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICE_PATTERN;\r\n" +
				"\r\n" +
				"import org.slf4j.Logger;\r\n" +
				"import org.slf4j.LoggerFactory;\r\n" +
				"\r\n" +
				"\r\n" +
				"import de.kreth.clubinvoice.Application_Properties;\r\n" +
				"\r\n" +
				"public class OverviewUi extends VerticalLayout implements InvoiceUi {\r\n" +
				"\r\n" +
				"	public void setContent(UI ui, VaadinRequest vaadinRequest) {\r\n" +
				"\r\n" +
				"	}\r\n" +
				"\r\n" +
				"	public VerticalLayout createInvoicesView(final UI ui) {\r\n" +
				"\r\n" +
				"		createInvoice = new Button(CAPTION_INVOICE_CREATE.getString(resBundle::getString));\r\n" +
				"		return right;\r\n" +
				"	}\r\n" +
				"\r\n" +
				"	public VerticalLayout createItemsView(final UI ui) {\r\n" +
				"\r\n" +
				"		addItem = new Button(de.kreth.clubinvoice.Application_Properties.CAPTION_INVOICE_CREATE.getString(resBundle::getString));\r\n"
				+
				"\r\n" +
				"		VerticalLayout left = new VerticalLayout();\r\n" +
				"		addItem = new Button(de.kreth.clubinvoice"
				+ "   \t.Application_Properties.CAPTION_INVOICE_CREATE.getString(resBundle::getString));\r\n" +
				"		left.addComponents(addItem, gridItems);\r\n" +
				"		left.setStyleName(STYLE_BORDERED);\r\n" +
				"		return left;\r\n" +
				"	}\r\n" +
				"\r\n" +
				"	public HorizontalLayout createHeadView(final UI ui, VaadinRequest vaadinRequest) {\r\n" +
				"		Label l2 = new Label(String.format(\"%s %s\", user.getPrename(), user.getSurname()));\r\n" +
				"\r\n" +
				"		Button addArticle = new Button(CAPTION_ARTICLES.getString(resBundle::getString));\r\n" +
				"		Button logoutButton = new Button(resBundle.getString(Application_Properties.LABEL_LOGOUT.getValue()));\r\n"
				+
				"		logoutButton.addClickListener(ev -> {\r\n" +
				"			LOGGER.warn(\"Logging out.\");\r\n" +
				"			logout(ui, vaadinRequest);\r\n" +
				"		});\r\n" +
				"\r\n" +
				"		Button userDetail = new Button(de.kreth.clubinvoice.Application_Properties.CAPTION_USER_DETAILS.getString(resBundle::getString), ev -> {\r\n"
				+
				"		Button userDetail2 = new Button(de.kreth.clubinvoice"
				+ "     \t.Application_Properties.CAPTION_USER_DETAILS.getString(resBundle::getString), ev -> {\r\n"
				+
				"			showUserDetailDialog(ui);\r\n" +
				"		});\r\n" +
				"\r\n" +
				"		return head;\r\n" +
				"	}\r\n" +
				"\r\n" +
				"}\r\n" +
				"";
	}
}
