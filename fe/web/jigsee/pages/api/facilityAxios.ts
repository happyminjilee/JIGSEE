import {axiosApi, axiosAuthApi} from '@/utils/instance'


export const getfacilitylist = async () => {
    const http = axiosAuthApi()
    return await http
        .get(
            'http://k10s105.p.ssafy.io/api/v1/facility/all'
        )
        .then((response) => {
            console.log(response.data.result)
            return response.data.result
        })
        .catch((error) => {
            console.log('facilities', error.message)
        })
}

export const getfacility = async (id: number) => {
    const http = axiosAuthApi()
    return await http
        .get(
            'http://k10s105.p.ssafy.io/api/v1/facility',
            {
                params: {
                    'facility-id' : id
                }
            },
        )
        .then((response) => {
            console.log(response.data.result)
            return response.data.result
        })
        .catch((error) => {
            console.log('facility', error.message)
        })
}

