package eu.europa.ec.grow.espd.business;

import com.google.common.base.Function;
import eu.europa.ec.grow.espd.criteria.enums.ExclusionCriterion;
import eu.europa.ec.grow.espd.criteria.enums.SelectionCriterion;
import eu.europa.ec.grow.espd.domain.Criterion;
import eu.europa.ec.grow.espd.domain.EspdDocument;
import eu.europa.ec.grow.espd.entities.CcvCriterion;
import isa.names.specification.ubl.schema.xsd.ccv_commonaggregatecomponents_1.CriterionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static eu.europa.ec.grow.espd.criteria.enums.ExclusionCriterion.*;
import static eu.europa.ec.grow.espd.criteria.enums.SelectionCriterion.*;

/**
 * Create the UBL {@link CriterionType} criteria for a ESPD Request, including both exclusion and selection
 * criteria.
 * <p/>
 * Created by ratoico on 11/26/15 at 5:19 PM.
 */
@Component
class UblRequestCriteriaTransformer implements Function<EspdDocument, List<CriterionType>> {

    private final UblCriterionTypeTransformer ublCriterionTypeTransformer;

    @Autowired
    UblRequestCriteriaTransformer(UblCriterionTypeTransformer ublCriterionTypeTransformer) {
        this.ublCriterionTypeTransformer = ublCriterionTypeTransformer;
    }

    @Override
    public List<CriterionType> apply(EspdDocument espdDocument) {
        List<CriterionType> criterionTypes = new ArrayList<>(
                ExclusionCriterion.values().length + SelectionCriterion.values().length + 1);
        criterionTypes.addAll(addExclusionCriteria(espdDocument));
        criterionTypes.addAll(addSelectionCriteria(espdDocument));
        return Collections.unmodifiableList(criterionTypes);
    }

    private List<CriterionType> addExclusionCriteria(EspdDocument espdDocument) {
        // we need to do it in a hard coded way right now, unfortunately
        // THE ORDER OF CRITERIA IS VERY IMPORTANT AND IT SHOULD BE COVERED BY THE TESTS
        List<CriterionType> criterionTypes = new ArrayList<>(ExclusionCriterion.values().length + 1);
        markSelectedExclusionCriminalConvictions(espdDocument, criterionTypes);
        markSelectedExclusionPayment(espdDocument, criterionTypes);
        markSelectedExclusionEnvironmental(espdDocument, criterionTypes);
        markSelectedExclusionBankruptcyInsolvency(espdDocument, criterionTypes);
        markSelectedExclusionMisconduct(espdDocument, criterionTypes);
        markSelectedExclusionConflictOfInterest(espdDocument, criterionTypes);
        markSelectedExclusionNationalGrounds(espdDocument, criterionTypes);
        return criterionTypes;
    }

    private void markSelectedExclusionCriminalConvictions(EspdDocument espdDocument, List<CriterionType> criteria) {
        addUblCriterionIfSelected(PARTICIPATION_CRIMINAL_ORGANISATION, espdDocument.getCriminalConvictions(), criteria);
        addUblCriterionIfSelected(CORRUPTION, espdDocument.getCorruption(), criteria);
        addUblCriterionIfSelected(FRAUD, espdDocument.getFraud(), criteria);
        addUblCriterionIfSelected(TERRORIST_OFFENCES, espdDocument.getTerroristOffences(), criteria);
        addUblCriterionIfSelected(MONEY_LAUNDERING, espdDocument.getMoneyLaundering(), criteria);
        addUblCriterionIfSelected(CHILD_LABOUR, espdDocument.getChildLabour(), criteria);
    }

    private void markSelectedExclusionPayment(EspdDocument espdDocument, List<CriterionType> criteria) {
        addUblCriterionIfSelected(PAYMENT_OF_TAXES, espdDocument.getPaymentTaxes(), criteria);
        addUblCriterionIfSelected(PAYMENT_OF_SOCIAL_SECURITY, espdDocument.getPaymentSocialSecurity(), criteria);
    }

    private void markSelectedExclusionEnvironmental(EspdDocument espdDocument, List<CriterionType> criteria) {
        addUblCriterionIfSelected(BREACHING_OF_OBLIGATIONS_ENVIRONMENTAL, espdDocument.getBreachingObligations(),
                criteria);
    }

    private void markSelectedExclusionBankruptcyInsolvency(EspdDocument espdDocument, List<CriterionType> criteria) {
        addUblCriterionIfSelected(BANKRUPTCY, espdDocument.getBankruptcy(), criteria);
        addUblCriterionIfSelected(INSOLVENCY, espdDocument.getInsolvency(), criteria);
        addUblCriterionIfSelected(ARRANGEMENT_WITH_CREDITORS, espdDocument.getArrangementWithCreditors(), criteria);
        addUblCriterionIfSelected(ANALOGOUS_SITUATION, espdDocument.getAnalogousSituation(), criteria);
        addUblCriterionIfSelected(ASSETS_ADMINISTERED_BY_LIQUIDATOR, espdDocument.getAssetsAdministeredByLiquidator(),
                criteria);
        addUblCriterionIfSelected(BUSINESS_ACTIVITIES_SUSPENDED, espdDocument.getBusinessActivitiesSuspended(),
                criteria);
    }

    private void markSelectedExclusionMisconduct(EspdDocument espdDocument, List<CriterionType> criteria) {
        addUblCriterionIfSelected(GUILTY_OF_PROFESSIONAL_MISCONDUCT, espdDocument.getGuiltyGrave(), criteria);
    }

    private void markSelectedExclusionConflictOfInterest(EspdDocument espdDocument, List<CriterionType> criteria) {
        addUblCriterionIfSelected(CONFLICT_OF_INTEREST_EO_PROCUREMENT_PROCEDURE, espdDocument.getConflictInterest(),
                criteria);
        addUblCriterionIfSelected(DIRECT_INVOLVEMENT_PROCUREMENT_PROCEDURE,
                espdDocument.getInvolvementPreparationProcurement(),
                criteria);
        addUblCriterionIfSelected(EARLY_TERMINATION, espdDocument.getEarlyTermination(), criteria);
        addUblCriterionIfSelected(GUILTY_OF_MISINTERPRETATION, espdDocument.getGuiltyMisinterpretation(), criteria);
    }

    private void markSelectedExclusionNationalGrounds(EspdDocument espdDocument, List<CriterionType> criteria) {
        addUblCriterionIfSelected(NATIONAL_EXCLUSION_GROUNDS, espdDocument.getPurelyNationalGrounds(), criteria);
    }

    private List<CriterionType> addSelectionCriteria(EspdDocument espdDocument) {
        if (espdDocument.satisfiesAllCriteria()) {
            List<CriterionType> all = new ArrayList<>(1);
            addUblCriterionIfSelected(SelectionCriterion.ALL_SELECTION_CRITERIA_SATISFIED,
                    espdDocument.getSelectionSatisfiesAll(), all);
            return Collections.unmodifiableList(all);
        }
        List<CriterionType> criterionTypes = new ArrayList<>(SelectionCriterion.values().length + 1);
        markSelectedSelectionSuitability(espdDocument, criterionTypes);
        markSelectedSelectionEconomicFinancialStanding(espdDocument, criterionTypes);
        markSelectedSelectionTechnicalProfessionalAbility(espdDocument, criterionTypes);
        return criterionTypes;
    }

    private void markSelectedSelectionSuitability(EspdDocument espdDocument, List<CriterionType> criteria) {
        addUblCriterionIfSelected(ENROLMENT_PROFESSIONAL_REGISTER, espdDocument.getEnrolmentProfessionalRegister(),
                criteria);
        addUblCriterionIfSelected(ENROLMENT_TRADE_REGISTER, espdDocument.getEnrolmentTradeRegister(), criteria);
        addUblCriterionIfSelected(SERVICE_CONTRACTS_AUTHORISATION, espdDocument.getServiceContractsAuthorisation(),
                criteria);
        addUblCriterionIfSelected(SERVICE_CONTRACTS_MEMBERSHIP, espdDocument.getServiceContractsMembership(), criteria);
    }

    private void markSelectedSelectionEconomicFinancialStanding(EspdDocument espdDocument,
            List<CriterionType> criteria) {
        addUblCriterionIfSelected(GENERAL_YEARLY_TURNOVER, espdDocument.getGeneralYearlyTurnover(), criteria);
        addUblCriterionIfSelected(AVERAGE_YEARLY_TURNOVER, espdDocument.getAverageYearlyTurnover(), criteria);
        addUblCriterionIfSelected(SPECIFIC_YEARLY_TURNOVER, espdDocument.getSpecificYearlyTurnover(), criteria);
        addUblCriterionIfSelected(SPECIFIC_AVERAGE_TURNOVER, espdDocument.getSpecificAverageTurnover(), criteria);
        addUblCriterionIfSelected(FINANCIAL_RATIO, espdDocument.getFinancialRatio(), criteria);
        addUblCriterionIfSelected(PROFESSIONAL_RISK_INSURANCE, espdDocument.getProfessionalRiskInsurance(), criteria);
        addUblCriterionIfSelected(OTHER_ECONOMIC_OR_FINANCIAL_REQUIREMENTS,
                espdDocument.getOtherEconomicFinancialRequirements(), criteria);
    }

    private void markSelectedSelectionTechnicalProfessionalAbility(EspdDocument espdDocument,
            List<CriterionType> criteria) {
        addUblCriterionIfSelected(WORK_CONTRACTS_PERFORMANCE_OF_WORKS,
                espdDocument.getWorkContractsPerformanceOfWorks(), criteria);
        addUblCriterionIfSelected(SUPPLY_CONTRACTS_PERFORMANCE_OF_DELIVERIES,
                espdDocument.getSupplyContractsPerformanceDeliveries(), criteria);
        addUblCriterionIfSelected(SERVICE_CONTRACTS_PERFORMANCE_OF_SERVICES,
                espdDocument.getServiceContractsPerformanceServices(), criteria);
        addUblCriterionIfSelected(TECHNICIANS_OR_TECHNICAL_BODIES, espdDocument.getTechniciansTechnicalBodies(),
                criteria);
        addUblCriterionIfSelected(WORK_CONTRACTS_TECHNICIANS_OR_TECHNICAL_BODIES,
                espdDocument.getWorkContractsTechnicians(), criteria);
        addUblCriterionIfSelected(TECHNICAL_FACILITIES_AND_MEASURES, espdDocument.getTechnicalFacilitiesMeasures(),
                criteria);
        addUblCriterionIfSelected(STUDY_AND_RESEARCH_FACILITIES,
                espdDocument.getStudyResearchFacilities(), criteria);
        addUblCriterionIfSelected(SUPPLY_CHAIN_MANAGEMENT, espdDocument.getSupplyChainManagement(), criteria);
        addUblCriterionIfSelected(ALLOWANCE_OF_CHECKS, espdDocument.getAllowanceOfChecks(), criteria);
        addUblCriterionIfSelected(EDUCATIONAL_AND_PROFESSIONAL_QUALIFICATIONS,
                espdDocument.getEducationalProfessionalQualifications(), criteria);
        addUblCriterionIfSelected(ENVIRONMENTAL_MANAGEMENT_FEATURES, espdDocument.getEnvironmentalManagementFeatures(),
                criteria);
        addUblCriterionIfSelected(NUMBER_OF_MANAGERIAL_STAFF, espdDocument.getNumberManagerialStaff(), criteria);
        addUblCriterionIfSelected(AVERAGE_ANNUAL_MANPOWER, espdDocument.getAverageAnnualManpower(), criteria);
        addUblCriterionIfSelected(TOOLS_PLANT_TECHNICAL_EQUIPMENT, espdDocument.getToolsPlantTechnicalEquipment(),
                criteria);
        addUblCriterionIfSelected(SUBCONTRACTING_PROPORTION, espdDocument.getSubcontractingProportion(), criteria);
        addUblCriterionIfSelected(SUPPLY_CONTRACTS_SAMPLES_DESCRIPTIONS_WITHOUT_CA,
                espdDocument.getSupplyContractsSamplesDescriptionsWithoutCa(), criteria);
        addUblCriterionIfSelected(SUPPLY_CONTRACTS_SAMPLES_DESCRIPTIONS_WITH_CA,
                espdDocument.getSupplyContractsSamplesDescriptionsWithCa(), criteria);
        addUblCriterionIfSelected(SUPPLY_CONTRACTS_CERTIFICATES_QC,
                espdDocument.getSupplyContractsCertificatesQc(), criteria);
        addUblCriterionIfSelected(CERTIFICATE_INDEPENDENT_BODIES_ABOUT_QA,
                espdDocument.getCertificateIndependentBodiesAboutQa(), criteria);
        addUblCriterionIfSelected(CERTIFICATE_INDEPENDENT_BODIES_ABOUT_ENVIRONMENTAL,
                espdDocument.getCertificateIndependentBodiesAboutEnvironmental(), criteria);
    }

    private void addUblCriterionIfSelected(CcvCriterion ccvCriterion, Criterion espdCriterion,
            List<CriterionType> ublCriteria) {
        if (isCriterionSelectedInEspd(espdCriterion)) {
            ublCriteria.add(ublCriterionTypeTransformer.apply(ccvCriterion));
        }
    }

    private boolean isCriterionSelectedInEspd(Criterion espdCriterion) {
        return espdCriterion != null && Boolean.TRUE.equals(espdCriterion.getExists());
    }

}