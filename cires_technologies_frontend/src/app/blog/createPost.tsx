import React, { useState } from 'react';

const CreatePostForm = () => {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [image, setImage] = useState('');

    const handleSubmit = async (event: any) => {
        event.preventDefault();

        try {
            const response = await fetch('/api/posts/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    title,
                    content,
                    author: 1,
                    date: new Date().toISOString(),
                    image,
                    likes: [],
                }),
            });

            if (response.ok) {
                console.log('Post created successfully');
                setTitle('');
                setContent('');
                setImage('');
            } else {
                console.error('Error creating post:', response.statusText);
            }
        } catch (error: any) {
            console.error('Error creating post:', error.message);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <label>
                Title:
                <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} />
            </label>
            <label>
                Content:
                <textarea value={content} onChange={(e) => setContent(e.target.value)} />
            </label>
            <label>
                Image URL:
                <input type="text" value={image} onChange={(e) => setImage(e.target.value)} />
            </label>
            <button type="submit">Create Post</button>
        </form>
    );
};

export default CreatePostForm;
