package com.yrc.ddstreamserver.service.feign.actuator

import com.yrc.ddstreamserver.service.feign.AbstractFeignClientFactory
import org.springframework.stereotype.Component

@Component
class ActuatorServiceFactory : AbstractFeignClientFactory<ActuatorService>(ActuatorService::class) {
}