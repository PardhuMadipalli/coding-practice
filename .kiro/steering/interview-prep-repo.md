# Interview Prep Repo — Structure & Update Guide

## What this repo is

A personal knowledge base and coding practice repo for interview preparation. It is also a Jekyll static site published at https://pardhumadipalli.github.io/coding-practice.

## Repo structure

```
index.md                  # Main DSA page (parent for all DSA sub-pages)
misc.md                   # Miscellaneous notes
dp/index.md               # Dynamic Programming concepts and problems
treeandgraph/graphs.md    # Graph algorithms and problems
databases/                # Database concepts (indexing, locks, etc.)
system-design/            # System design notes and links
javapractice/             # Java language practice files and notes
problems/
  miscellaneous/          # General LeetCode problem solutions (.java)
  dp/                     # DP problem solutions (.java)
  backtracking/           # Backtracking problem solutions (.java)
  binarysearch/           # Binary search problem solutions (.java)
  python-problems/        # Python solutions
sorting/                  # Sorting algorithm implementations
bfsshortreach/            # BFS problem files
```

## Jekyll front matter

Every `.md` page that appears in the site navigation needs front matter:

```yaml
---
layout: custom_page
parent: DSA          # or the correct parent page title
title: Page Title
---
```

- `parent` must match the `title` of the parent page exactly
- `layout: custom_page` is used for all content pages
- `{{ site.code_path }}` in markdown expands to `https://github.com/PardhuMadipalli/coding-practice/blob/main/` — use this for linking to Java solution files

## When and how to update

### After discussing a DSA problem
1. If a Java solution was written, save it in the appropriate folder:
   - General problems → `problems/miscellaneous/`
   - DP problems → `problems/dp/`
   - Backtracking → `problems/backtracking/`
   - Binary search → `problems/binarysearch/`
2. Add the problem to the relevant markdown page's problems table with a short description of the approach and a link to the solution file using `{{ site.code_path }}problems/...`
3. If the problem introduces a new concept or algorithm, add a concept section to the relevant `.md` page

### After discussing a concept (algorithm, data structure, technique)
- Add it to the relevant topic page (`dp/index.md`, `treeandgraph/graphs.md`, etc.)
- Keep bullet points short and focused — this is a reference, not a tutorial
- Use `$$...$$` for block math and `$...$` for inline math (kramdown/MathJax)

### Adding a new topic page
1. Create a new `.md` file in an appropriate folder (e.g., `strings/index.md`)
2. Add correct front matter with `parent: DSA`
3. Add a short link entry in `index.md` under the relevant section pointing to the new page

### System design notes
- Go in `system-design/index.md` or `system-design/important-links.md`

### Java practice / language notes
- Go in `javapractice/index.md` and the `.java` files in `javapractice/`

## Committing changes

After making updates, commit with a descriptive message:

```
git add -A
git commit -m "docs: add <problem/concept name> notes and solution"
```

Follow conventional commits format:
- `docs:` for markdown note updates
- `feat:` for new solution files
- `fix:` for corrections

Do NOT run `git push`.

## Style conventions for markdown pages

- Problem tables use two columns: `| Problem | Solution & Details |`
- Keep solution descriptions to 1-2 lines — focus on the key insight
- Link to LeetCode problem in the Problem column
- Link to the Java solution file in the Solution column using `{{ site.code_path }}`
- Use `**bold**` sparingly, only for truly important callouts
- Concept sections use `###` headings, sub-concepts use `####`
