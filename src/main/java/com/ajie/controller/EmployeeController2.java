//    /**
//     * 员工退出
//     * @param request
//     * @return
//     */
//    @PostMapping("/logout")
//    public R<String> logout(HttpServletRequest request){
//        //清理Session中保存的当前登录员工的id
//        request.getSession().removeAttribute("employee");
//        return R.success("退出成功");
//    }
//}
