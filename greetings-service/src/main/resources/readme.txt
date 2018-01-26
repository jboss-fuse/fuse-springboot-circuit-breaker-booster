Resources under /static are just a client only stripped down version of hystrix-dasboard,
to upgrade/maintain this just:
1) copy form hystrix-dashboard/tree/master/src/main/webapp in to greetings-service/src/main/resources/static
2) remove WEB-INF dir, index.css and index.html
3) apply changes at https://github.com/valdar/hystrix-dashboard/commit/2c37997d4d6fe8b0d3f7fa291174ab747f85a07b#diff-61c493efcf38083126d32a9d11ceeaff
   (waiting while this will get merged: https://github.com/Netflix-Skunkworks/hystrix-dashboard/pull/2)
