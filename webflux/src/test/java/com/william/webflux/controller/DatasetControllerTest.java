package com.william.webflux.controller;

import com.william.webflux.dto.DatasetCountResponse;
import com.william.webflux.exception.DatasetNotFoundException;
import com.william.webflux.service.DatasetQueryService;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DatasetControllerTest {
	private final DatasetQueryService datasetQueryService = mock(DatasetQueryService.class);
	private final DatasetController controller = new DatasetController(datasetQueryService);

	@Test
	void listsDatasets() {
		given(datasetQueryService.listDatasets()).willReturn(Flux.just("artists"));

		List<String> datasets = controller.listDatasets()
				.collectList()
				.block(Duration.ofSeconds(1));

		assertEquals(List.of("artists"), datasets);
	}

	@Test
	void returnsCount() {
		given(datasetQueryService.count("artists")).willReturn(Mono.just(42L));

		DatasetCountResponse response = controller.count("artists")
				.block(Duration.ofSeconds(1));

		assertEquals("artists", response.dataset());
		assertEquals(42L, response.count());
	}

	@Test
	void throwsWhenDatasetMissing() {
		given(datasetQueryService.count("missing"))
				.willReturn(Mono.error(new DatasetNotFoundException("missing")));

		assertThrows(DatasetNotFoundException.class,
				() -> controller.count("missing").block(Duration.ofSeconds(1)));
	}
}


