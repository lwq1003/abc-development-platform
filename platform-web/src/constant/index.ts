/**
 * 将模块中的常量汇集到这里， 使用方式保持不变 this.constant.XXX
 */
import * as common from './common'
import * as system from '@/modules/system/constant'
import * as entityconfig from '@/modules/entityconfig/constant'
import * as cip from '@/modules/cip/constant'

export default {
  ...common,
  ...system,
  ...entityconfig,
  ...cip
}
