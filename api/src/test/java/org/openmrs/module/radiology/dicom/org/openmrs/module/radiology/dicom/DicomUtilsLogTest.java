package org.openmrs.module.radiology.dicom;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.module.radiology.RadiologyConstants;
import org.openmrs.module.radiology.RadiologyProperties;
import org.openmrs.module.radiology.study.RadiologyStudyService;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
public class DicomUtilsLogTest{

    @Mock
    private Appender mockAppender;
    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    private static final String TEST_DATASET = "org/openmrs/module/radiology/include/DicomUtilsComponentTestDataset.xml";

    protected static final String DICOM_SPECIFIC_CHARACTER_SET = "ISO-8859-1";

    @Autowired
    @Qualifier("adminService")
    private AdministrationService administrationService;

    @Rule
    public TemporaryFolder temporaryBaseFolder = new TemporaryFolder();

    @Before
    public void setup() throws Exception {
//        administrationService.saveGlobalProperty(new GlobalProperty(RadiologyConstants.GP_DICOM_SPECIFIC_CHARCATER_SET,
//                DICOM_SPECIFIC_CHARACTER_SET));

        when(mockAppender.getName()).thenReturn("MockAppender");
//        when(mockAppender.isStarted()).thenReturn(true);
//        when(mockAppender.isStopped()).thenReturn(false);
        Logger root = Logger.getLogger(DicomUtils.class);
        root.addAppender(mockAppender);
        root.setLevel(Level.INFO);
    }

    /**
     * @verifies catch NullPointerException
     * @see DicomUtils#updateStudyPerformedStatusByMpps(org.dcm4che2.data.DicomObject)
     */
    @Test
    public void updateStudyPerformedStatusByMpps_shouldCatchNullPointerException(){
        DicomUtils.updateStudyPerformedStatusByMpps(null);
        verify(mockAppender, times(1)).doAppend(captorLoggingEvent.capture());
        System.out.println(captorLoggingEvent.getAllValues().get(0));
    }

//	/**
//	 * @verifies catch NumberFormatException
//	 * @see DicomUtils#updateStudyPerformedStatusByMpps(org.dcm4che2.data.DicomObject)
//	 */
//	@org.junit.Test(expected=NumberFormatException.class)
//	public void updateStudyPerformedStatusByMpps_shouldCatchNumberFormatException() throws Exception {
//		RadiologyStudy studyToBeUpdated = radiologyStudyService.getStudyByStudyId(STUDY_ID_OF_EXISTING_STUDY_WITH_ORDER);
//		Order radiologyOrder = studyToBeUpdated.getRadiologyOrder();
//		DicomObject dicomObjectNCreate = getDicomNSet(studyToBeUpdated, radiologyOrder, "COMPLETED");
//		DicomUtils.updateStudyPerformedStatusByMpps(dicomObjectNCreate);
//	}
}
