package com.william.tomcat.controller;

import com.william.tomcat.dto.DatasetCountResponse;
import com.william.tomcat.exception.DatasetNotFoundException;
import com.william.tomcat.service.DatasetQueryProvider;
import com.william.tomcat.service.DatasetQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DatasetControllerTest {
	private final DatasetQueryProvider datasetQueryProvider = mock(DatasetQueryProvider.class);
	private final DatasetQueryService datasetQueryService = mock(DatasetQueryService.class);
	private final DatasetController controller = new DatasetController(datasetQueryProvider);

	@BeforeEach
	void setUp() {
		given(datasetQueryProvider.forMode("jdbc")).willReturn(datasetQueryService);
	}

	@Test
	void listsDatasets() {
		given(datasetQueryService.listDatasets()).willReturn(List.of("artists"));

		List<String> datasets = controller.listDatasets("jdbc");

		assertEquals(List.of("artists"), datasets);
	}

	@Test
	void returnsCount() {
		given(datasetQueryService.count("artists")).willReturn(42L);

		DatasetCountResponse response = controller.count("artists", "jdbc");

		assertEquals("artists", response.dataset());
		assertEquals(42L, response.count());
	}

	@Test
	void throwsWhenDatasetMissing() {
		given(datasetQueryService.count("missing")).willThrow(new DatasetNotFoundException("missing"));

		assertThrows(DatasetNotFoundException.class, () -> controller.count("missing", "jdbc"));
	}
}


