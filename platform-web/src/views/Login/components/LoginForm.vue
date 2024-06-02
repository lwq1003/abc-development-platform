<script setup lang="ts">
import { reactive, ref, unref, watch, computed } from 'vue'
import { Form } from '@/components/Form'
import { useI18n } from '@/hooks/web/useI18n'
import { ElButton } from 'element-plus'
import { useForm } from '@/hooks/web/useForm'
import { loginApi } from '@/api/login'

import { useAppStore } from '@/store/modules/app'
import { useUserStore } from '@/store/modules/user'
import { usePermissionStore } from '@/store/modules/permission'
import type { RouteLocationNormalizedLoaded, RouteRecordRaw } from 'vue-router'
import { useRouter } from 'vue-router'
import { UserType } from '@/api/login/types'
import { useValidator } from '@/hooks/web/useValidator'
import { FormSchema } from '@/types/form'
import useGetGlobalProperties from '@/hooks/useGlobal'

const globalProperties = useGetGlobalProperties()

const { required } = useValidator()

const emit = defineEmits(['to-register'])

const userStore = useUserStore()

const permissionStore = usePermissionStore()

const { currentRoute, addRoute, push } = useRouter()

const { t } = useI18n()

const rules = {
  username: [required()],
  password: [required()]
}

//读取 是否启用注册 配置
const appStore = useAppStore()
const enableRegister = computed(() => appStore.getEnableRegister)
const schema = reactive<FormSchema[]>([
  {
    field: 'title',
    colProps: {
      span: 24
    }
  },
  {
    field: 'username',
    label: t('login.username'),
    value: 'admin',
    component: 'Input',
    colProps: {
      span: 24
    },
    componentProps: {
      style: {
        width: '100%'
      },
      placeholder: t('login.usernamePlaceholder')
    }
  },
  {
    field: 'password',
    label: t('login.password'),
    value: '12345678',
    component: 'InputPassword',
    colProps: {
      span: 24
    },
    componentProps: {
      style: {
        width: '100%'
      },
      placeholder: t('login.passwordPlaceholder')
    }
  },
  {
    field: 'login',
    colProps: {
      span: 24
    }
  },
  {
    field: 'tool',
    colProps: {
      span: 24
    }
  }
])

const iconSize = 30

// const remember = ref(false)

const { register, elFormRef, methods } = useForm()

const loading = ref(false)

const iconColor = '#999'

const redirect = ref<string>('')

watch(
  () => currentRoute.value,
  (route: RouteLocationNormalizedLoaded) => {
    redirect.value = route?.query?.redirect as string
  },
  {
    immediate: true
  }
)
const keyUp = (event) => {
  if (event.key === 'Enter') {
    signIn()
  }
}

// 登录
const signIn = async () => {
  const formRef = unref(elFormRef)
  await formRef?.validate(async (isValid) => {
    if (isValid) {
      loading.value = true
      const { getFormData } = methods
      const formData = await getFormData<UserType>()

      try {
        const res = await loginApi(formData)

        if (res) {
          // 保存用户信息
          userStore.setUserAction(res.data)
          // 登录成功，启动websocket连接
          globalProperties.$webSocket.init()
          // 使用动态路由
          const routers = res.data.menuPermission || []
          await permissionStore.generateRoutes(routers).catch(() => {})

          permissionStore.getAddRouters.forEach((route) => {
            // 动态添加可访问路由表
            addRoute(route as RouteRecordRaw)
          })
          permissionStore.setIsAddRouters(true)
          // 设置首页
          push({ path: '/desktop/index' })
        }
      } finally {
        loading.value = false
      }
    }
  })
}

// 去注册页面
const toRegister = () => {
  emit('to-register')
}

// 打开找回密码对话框
import RetrievePasswordForm from './RetrievePassword.vue'
const retrievePasswordForm = ref(null)
const retrievePassword = () => {
  retrievePasswordForm.value.init()
}
</script>

<template>
  <Form
    :schema="schema"
    :rules="rules"
    label-position="top"
    hide-required-asterisk
    size="large"
    class="dark:(border-1 border-[var(--el-border-color)] border-solid)"
    @register="register"
  >
    <template #title>
      <h2 class="text-2xl font-bold text-center w-[100%]">{{ t('login.login') }}</h2>
    </template>

    <template #tool>
      <div class="flex justify-end items-center w-[100%]" v-show="enableRegister">
        <!-- <ElCheckbox v-model="remember" :label="t('login.remember')" size="small" /> -->
        <ElLink type="primary" :underline="false" @click="retrievePassword">{{
          t('login.forgetPassword')
        }}</ElLink>
      </div>
    </template>

    <template #login>
      <div class="w-[100%]">
        <ElButton :loading="loading" type="primary" class="w-[100%]" @click="signIn">
          {{ t('login.login') }}
        </ElButton>
      </div>
      <div class="w-[100%] mt-15px" v-show="enableRegister">
        <ElButton class="w-[100%]" @click="toRegister">
          {{ t('login.register') }}
        </ElButton>
      </div>
    </template>

    <template #otherIcon>
      <div class="flex justify-between w-[100%]">
        <Icon
          icon="ant-design:github-filled"
          :size="iconSize"
          class="cursor-pointer anticon"
          :color="iconColor"
        />
        <Icon
          icon="ant-design:wechat-filled"
          :size="iconSize"
          class="cursor-pointer anticon"
          :color="iconColor"
        />
        <Icon
          icon="ant-design:alipay-circle-filled"
          :size="iconSize"
          :color="iconColor"
          class="cursor-pointer anticon"
        />
        <Icon
          icon="ant-design:weibo-circle-filled"
          :size="iconSize"
          :color="iconColor"
          class="cursor-pointer anticon"
        />
      </div>
    </template>
  </Form>
  <RetrievePasswordForm ref="retrievePasswordForm" />
</template>

<style lang="less" scoped>
:deep(.anticon) {
  &:hover {
    color: var(--el-color-primary) !important;
  }
}
</style>
