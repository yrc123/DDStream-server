package com.yrc.ddstreamserver.service.feign.actuator

import com.yrc.ddstreamserver.service.feign.AbstractFeignClientFactory

class ActuatorServiceFactory : AbstractFeignClientFactory<ActuatorService>(ActuatorService::class){
}