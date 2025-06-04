package jpabook.jpashop.controller.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jpabook.jpashop.entity.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    @GetMapping("/api/v2/members")
    public Result<List<MemberDto>> membersV2() {
        List<Member> findMembers = memberService.findMembers();
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .toList();
        return new Result<>(collect.size(), collect);
    }

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberResponseV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request
    ) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    public static class CreateMemberRequest {
        @NotEmpty private String name;
    }

    @AllArgsConstructor
    @Data
    public static class CreateMemberResponse {
        private Long id;
    }

    @Data
    public static class UpdateMemberRequest {
        @NotEmpty private String name;
    }

    @AllArgsConstructor
    @Data
    public static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @AllArgsConstructor
    @Data
    public static class MemberDto {
        private String name;
    }

    @AllArgsConstructor
    @Data
    public static class Result<T> {
        private int count;
        private T data;
    }

}
