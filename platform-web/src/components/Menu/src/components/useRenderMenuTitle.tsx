import type { RouteMeta } from 'vue-router'

import { useI18n } from '@/hooks/web/useI18n'
import IconDisplay from '@/components/abc/IconDisplay/index.vue'
export const useRenderMenuTitle = () => {
  const renderMenuTitle = (meta: RouteMeta) => {
    const { t } = useI18n()
    const { title = 'Please set title', icon } = meta
    if (icon) {
      return (
        <>
          <el-icon>
            <IconDisplay v-model={icon}></IconDisplay>
          </el-icon>
          <span class="v-menu__title">{t(title as string)}</span>
        </>
      )
    } else {
      return <span class="v-menu__title">{t(title as string)}</span>
    }

    // return icon ? (
    //   <>
    //     <el-icon>
    //       <Delete />
    //     </el-icon>
    //     <span class="v-menu__title">{t(title as string)}</span>
    //   </>
    // ) : (
    //   <span class="v-menu__title">{t(title as string)}</span>
    // )
  }

  return {
    renderMenuTitle
  }
}
