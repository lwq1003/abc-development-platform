<template>
  <UserSingleSelect v-model="userId" />
</template>
<script lang="ts">
import UserSingleSelect from '@/modules/system/view/user/treeListReference.vue'
export default {
  name: 'CommonSelect',
  components: { UserSingleSelect },

  data() {
    return {
      userId: ''
    }
  },
  methods: {}
}
</script>

<style></style>
