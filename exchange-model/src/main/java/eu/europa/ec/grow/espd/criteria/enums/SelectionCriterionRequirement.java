package eu.europa.ec.grow.espd.criteria.enums;

import eu.europa.ec.grow.espd.entities.CcvCriterionRequirement;
import lombok.Getter;

/**
 * Created by ratoico on 12/8/15 at 1:45 PM.
 */
@Getter
public enum SelectionCriterionRequirement implements CcvCriterionRequirement {

    /**
     *
     */
    ANSWER_YES_NO("7e7db838-eeac-46d9-ab39-42927486f22d", CriterionText.ANSWER_YES_NO_TEXT,
            ExpectedResponseType.INDICATOR),
    /**
     *
     */
    YEAR_AMOUNT_CURRENCY("7e7db838-eeac-46d9-ab39-42927486f22d", CriterionText.YEAR_AMOUNT_CURRENCY_TEXT,
            ExpectedResponseType.AMOUNT),
    /**
     *
     */
    REQUIRED_RATIO("e4d37adc-08cd-4f4d-a8d8-32b62b0a1f46", CriterionText.REQUIRED_RATIO_TEXT,
            ExpectedResponseType.RATIO),
    /**
     *
     */
    AMOUNT_CURRENCY("7604bd40-4462-4086-8763-a50da51a869c", CriterionText.AMOUNT_CURRENCY_TEXT,
            ExpectedResponseType.AMOUNT),
    /**
     *
     */
    PLEASE_SPECIFY("3aaca389-4a7b-406b-a4b9-080845d127e7", CriterionText.PLEASE_SPECIFY_TEXT,
            ExpectedResponseType.DESCRIPTION),
    /**
     *
     */
    PERCENTAGE("612a1625-118d-4ea4-a6db-413184e7c0a8", CriterionText.PERCENTAGE_TEXT,
            ExpectedResponseType.PERCENTAGE),
    /**
     *
     */
    PLEASE_ENUMERATE("07301031-2270-41af-8e7e-66fe0c777107", CriterionText.PLEASE_ENUMERATE_TEXT,
            ExpectedResponseType.DESCRIPTION),
    /**
     *
     */
    PLEASE_DESCRIBE("51391308-0bf6-423c-95e2-d5a54aa31fb8", CriterionText.PLEASE_DESCRIBE_TEXT,
            ExpectedResponseType.DESCRIPTION),
    /**
     *
     */
    DESCRIPTION("ab05ff3b-f3e1-4441-9b43-ee9912e29e92", CriterionText.DESCRIPTION,
            ExpectedResponseType.DESCRIPTION),
    /**
     *
     */
    AMOUNT("42db0eaa-d2dd-48cb-83ac-38d73cab9b50", CriterionText.AMOUNT,
            ExpectedResponseType.AMOUNT),
    /**
     *
     */
    DATE("42ec8116-31a7-4118-8612-5b04f5c8bde7", CriterionText.DATE,
            ExpectedResponseType.DATE),
    /**
     *
     */
    RECIPIENTS("a92536ab-6783-40bb-a037-5d31f421fd85", CriterionText.RECIPIENTS,
            ExpectedResponseType.TEXT),
    /**
     *
     */
    INFO_AVAILABLE_ELECTRONICALLY("9dae5670-cb75-4c97-901b-96ddac5a633a",
            CriterionText.INFO_AVAILABLE_ELECTRONICALLY_TEXT, ExpectedResponseType.INDICATOR),
    /**
     *
     */
    URL("03bb1954-13ae-47d8-8ef8-b7fe0f22d700", CriterionText.URL_TEXT, ExpectedResponseType.URL),
    /**
     *
     */
    URL_CODE("e2d863a0-60cb-4e58-8c14-4c1595af48b7", CriterionText.URL_CODE_TEXT, ExpectedResponseType.TEXT),
    ;

    private final String id;

    private final String description;

    private final ExpectedResponseType responseType;

    SelectionCriterionRequirement(String id, String description, ExpectedResponseType responseType) {
        this.id = id;
        this.description = description;
        this.responseType = responseType;
    }
}