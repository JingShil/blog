import request from '@/utils/request'

export function list(pageNumber, pageSize) {
	return request({
		url: 'blog/list/',
		method: 'post',
		params:{
			pageNumber:pageNumber,
			pageSize:pageSize
		}
	})
}