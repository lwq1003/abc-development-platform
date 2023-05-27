/**
 * 将模块中的常量汇集到这里， 使用方式保持不变 this.constant.XXX
 */
import * as common from './common'
import * as system from '@/modules/system/constant'
import * as support from '@/modules/support/constant'
import * as entityconfig from '@/modules/entityconfig/constant'
import * as cip from '@/modules/cip/constant'
import * as scheduler from '@/modules/scheduler/constant'
import * as notification from '@/modules/notification/constant'
export default {
  ...common,
  ...system,
  ...support,
  ...entityconfig,
  ...cip,
  ...scheduler,
  ...notification
}
