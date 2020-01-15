select * from idea
join retort on retort.idea = idea.id
join message on retort.id = message.retort_id