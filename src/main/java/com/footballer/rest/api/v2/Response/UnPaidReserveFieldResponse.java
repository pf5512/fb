package com.footballer.rest.api.v2.Response;

public class UnPaidReserveFieldResponse extends JsonResponse {
	private Long fieldReservationId;

	public Long getFieldReservationId() {
		return fieldReservationId;
	}

	public void setFieldReservationId(Long fieldReservationId) {
		this.fieldReservationId = fieldReservationId;
	}
}
